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

import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
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
				Level1.class,
				Level2.class,
				Level3.class,
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

	// Add your tests, using standard JUnit.
	@Test
	public void hhh18436Test() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		Level1 root = new Level1(1L);

		Level2 child1 = new Level2(root, 1L);
		Level2 child2 = new Level2(root,2L);
		Level2 child3 = new Level2(root,3L);

		new Level3(child2,1L);

		s.persist(root);
		s.flush();
		s.clear();

		Level1 loadedWithoutEntityGraph = s.find(Level1.class, 1L);

		long i = 1;
		for (Level2 child : loadedWithoutEntityGraph.getChilds()) {
			Assert.assertEquals("Childs not in expected order", Long.valueOf(i), child.getId());
			i++;
		}

		tx.commit();
		s.close();
	}

	// Add your tests, using standard JUnit.
	@Test
	public void hhh18436TestEntitygraph() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		Level1 root = new Level1(1L);

		Level2 child1 = new Level2(root, 1L);
		Level2 child2 = new Level2(root,2L);
		Level2 child3 = new Level2(root,3L);

		new Level3(child2,1L);

		s.persist(root);
		s.flush();
		s.clear();

		// Hints
		Map<String, Object> hints = new HashMap<>();
		hints.put("jakarta.persistence.loadgraph", s.getEntityGraph("level1_loadAll"));

		Level1 loadedWithEntityGraph = s.find(Level1.class, 1L, hints);

		long i = 1;
		for (Level2 child : loadedWithEntityGraph.getChilds()) {
			Assert.assertEquals("Childs not in expected order", Long.valueOf(i), child.getId());
			i++;
		}

		// Do stuff...
		tx.commit();
		s.close();
	}
}
