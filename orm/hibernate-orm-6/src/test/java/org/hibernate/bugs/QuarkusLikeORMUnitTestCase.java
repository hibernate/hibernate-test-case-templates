/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.bugs.entity.AbsOne;
import org.hibernate.bugs.entity.AbsTwo;
import org.hibernate.bugs.entity.AbsThree;
import org.hibernate.bugs.entity.One;
import org.hibernate.bugs.entity.Two;
import org.hibernate.bugs.entity.Three;
import org.hibernate.bugs.entity.Four;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributeLoadingInterceptor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.id.SequenceMismatchStrategy;
import org.hibernate.id.enhanced.StandardOptimizerDescriptor;
import org.hibernate.loader.BatchFetchStyle;
import org.hibernate.query.NullPrecedence;

import org.hibernate.testing.bytecode.enhancement.BytecodeEnhancerRunner;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * <p>
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
@RunWith(BytecodeEnhancerRunner.class) // This runner enables bytecode enhancement for your test.
public class QuarkusLikeORMUnitTestCase extends BaseCoreFunctionalTestCase {

	// Add your entities here.
	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {
				AbsOne.class,
				AbsTwo.class,
				AbsThree.class,
				One.class,
				Two.class,
				Three.class,
				Four.class
		};
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		// For your own convenience to see generated queries:
		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );

		// Other settings that will make your test case run under similar configuration that Quarkus is using by default:
		configuration.setProperty( AvailableSettings.PREFERRED_POOLED_OPTIMIZER, StandardOptimizerDescriptor.POOLED_LO.getExternalName() );
		configuration.setProperty( AvailableSettings.DEFAULT_BATCH_FETCH_SIZE, "16" );
		configuration.setProperty( AvailableSettings.BATCH_FETCH_STYLE, BatchFetchStyle.PADDED.toString() );
		configuration.setProperty( AvailableSettings.QUERY_PLAN_CACHE_MAX_SIZE, "2048" );
		configuration.setProperty( AvailableSettings.DEFAULT_NULL_ORDERING, NullPrecedence.NONE.toString().toLowerCase( Locale.ROOT) );
		configuration.setProperty( AvailableSettings.IN_CLAUSE_PARAMETER_PADDING, "true" );
		configuration.setProperty( AvailableSettings.SEQUENCE_INCREMENT_SIZE_MISMATCH_STRATEGY, SequenceMismatchStrategy.NONE.toString() );

		// Add your own settings that are a part of your quarkus configuration:
		// configuration.setProperty( AvailableSettings.SOME_CONFIGURATION_PROPERTY, "SOME_VALUE" );
	}

	@Test
	public void parameterizedGetterMustFireLazyLoad() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
		final var insertedOne = insertSomeDataToDb();

		Session s = openSession();
		Transaction tx = s.beginTransaction();

		final var one1 = s.find(One.class, insertedOne.getId());
		final var two1 = one1.getTwo(); //this doesn't fire $$_hibernate_interceptor, so null is returned
		final var three1 = two1.getThree();
		final var four1 = three1.getFour();

		tx.commit();
		s.close();
	}

	@Test
	public void nonParameterizedGetterFromAbstractClassMustFireLazyLoad() throws Exception {
		final var insertedOne = insertSomeDataToDb();

		Session s = openSession();
		Transaction tx = s.beginTransaction();

		final var one1 = s.find(One.class, insertedOne.getId());
		final var two1 = one1.getTwo();
		final var absTwoStringProp = two1.getAbsTwoStringProp(); //this fires lazy load, so next line will not return null
		final var three1 = two1.getThree();
		final var threeStringProp = three1.getAbsThreeStringProp();  //this fires lazy load, three1.getFour() will not return null
		final var four1 = three1.getFour();
		final var fourConcrete = four1.getFourConcreteProp();

		tx.commit();
		s.close();
	}

	@Test
	public void anotherWayToSeeIfInterceptorGetsTriggered() throws InvocationTargetException, IllegalAccessException {
		final var one = new One();

		AtomicInteger numberOfInvocationsOfReadObject = new AtomicInteger(0);
		final var mockInterceptor = new LazyAttributeLoadingInterceptor(null, null, Set.of(), null)
		{
			@Override
			public Object readObject(Object obj, String name, Object oldValue) {
				numberOfInvocationsOfReadObject.incrementAndGet();
				return null;
			}
		};

		final var $$_hibernateSetInterceptor = Arrays.stream(one.getClass().getMethods()).filter(m -> m.getName().equals("$$_hibernate_setInterceptor")).findFirst().orElseThrow();
		$$_hibernateSetInterceptor.invoke(one, mockInterceptor);

		one.getOneConcreteProp();
		one.getAbsOneStringProp();
		one.getTwo();

		Assertions.assertEquals(3, numberOfInvocationsOfReadObject.get()); //one.getTwo() does not trigger it.
	}

	private One insertSomeDataToDb() {
		Session s = openSession();
		Transaction tx = s.beginTransaction();

		final var one = new One();
		final var two = new Two();
		final var three = new Three();
		final var four = new Four();

		one.setTwo(two);
		two.getOnes().add(one);
		two.setThree(three);
		three.getTwos().add(two);
		three.setFour(four);
		four.getThrees().add(three);

		one.setOneConcreteProp("oneConcrete");
		one.setAbsOneStringProp("oneAbs");
		two.setAbsTwoStringProp("twoAbs");
		two.setTwoConcreteProp("twoConcrete");
		three.setAbsThreeStringProp("threeAbs");
		three.setThreeConcreteProp("threeConcrete");
		four.setFourConcreteProp("fourConcrete");

		s.persist(one);
		s.flush();
		tx.commit();
		s.close();
		return one;
	}
}
