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

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.TestForIssue;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using its built-in unit test framework. Although ORMStandaloneTestCase is
 * perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing
 * your reproducer using this method simplifies the process.
 *
 * What's even better? Fork hibernate-orm itself, add your test case directly to
 * a module's unit tests, then submit it as a PR!
 */

@TestForIssue(jiraKey="HHH-13004")
public class ORMUnitTestCase extends BaseCoreFunctionalTestCase {

	// Add your entities here.
	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] { ParentEntity.class, ManyToManyEntity.class, NestedEntity.class };
	}

	// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
	@Override
	protected String[] getMappings() {
		return new String[] {
				// "Foo.hbm.xml",
				// "Bar.hbm.xml"
		};
	}

	// If those mappings reside somewhere other than resources/org/hibernate/test,
	// change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test. See
	// resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure(configuration);

		configuration.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());
		configuration.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());
		// configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	// Add your tests, using standard JUnit.

	@Test
	public void hhh13004Test() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and
		// provides the Session.
		// s = openSession();
		// Transaction tx = s.beginTransaction();

		EntityManager em = sessionFactory().createEntityManager();

		Session session = (Session) em.getDelegate();
		Transaction tx = session.beginTransaction();

		ParentEntity parentEntity = new ParentEntity();
		em.persist(parentEntity);

		NestedEntity nested1 = new NestedEntity();
		nested1.setName("bb");
		em.persist(nested1);

		NestedEntity nested2 = new NestedEntity();
		nested2.setName("aa");
		em.persist(nested2);

		parentEntity.addNested(nested1);
		parentEntity.addNested(nested2);

		em.flush();

		em.clear();

		// force use of loader

		ParentEntity parent = em.createQuery("select p from parent p", ParentEntity.class).getSingleResult();
		Assert.assertNotNull(parent);

		Assert.assertEquals(2, parent.getChildren().size());

		Assert.assertEquals("aa", parent.getChildren().get(0).getNested().getName());
		Assert.assertEquals("bb", parent.getChildren().get(1).getNested().getName());

		tx.commit();
		session.close();
	}

}
