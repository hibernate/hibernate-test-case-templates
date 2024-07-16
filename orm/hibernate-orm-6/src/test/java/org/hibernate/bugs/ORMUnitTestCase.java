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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 *
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
public class ORMUnitTestCase extends BaseCoreFunctionalTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
				BaseEntity.class,
				EntityA.class,
				EntityB.class
		};
	}

	// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
	@Override
	protected String[] getMappings() {
		return new String[] {
//				"Foo.hbm.xml",
//				"Bar.hbm.xml"
		};
	}
	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	@Override
	protected void prepareTest() throws Exception {
		inTransaction( s -> {
			s.createMutationQuery( "delete from EntityB" ).executeUpdate();
			s.createMutationQuery( "delete from EntityA" ).executeUpdate();
		} );

		inTransaction( s -> {
			EntityA entityA = new EntityA();
			entityA.name = "First Entity";
			entityA.columnAEntityA = 10L;
			entityA.columnBEntityA = 11L;
			s.persist( entityA );

			EntityB entityB = new EntityB();
			entityB.name = "Second Entity";
			entityB.columnAEntityB = 10L;
			entityB.columnBEntityB = 11L;
			s.persist( entityB );
		} );
	}

	// Add your tests, using standard JUnit.
	@Test
	public void findBothEntities() throws Exception {
		inTransaction( s -> {
			EntityA foundEntityA = s.createSelectionQuery( "from EntityA", EntityA.class ).getSingleResult();

			assertNotNull( foundEntityA );
			assertEquals( "First Entity", foundEntityA.name );
			assertNotNull( foundEntityA.entityB );
			assertEquals( "Second Entity", foundEntityA.entityB.name );
		} );
	}

	@Test
	public void findBothEntitiesInReverse() throws Exception {
		inTransaction( s -> {
			EntityB foundEntityB = s.createSelectionQuery( "from EntityB", EntityB.class ).getSingleResult();

			assertNotNull( foundEntityB );
			assertEquals( "Second Entity", foundEntityB.name );
			assertNotNull( foundEntityB.entityA );
			assertEquals( "First Entity", foundEntityB.entityA.name );
		} );
	}
}
