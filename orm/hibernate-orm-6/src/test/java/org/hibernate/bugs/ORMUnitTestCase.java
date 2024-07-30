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

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.TenantId;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
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
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] {
			TestEntity.class
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
		configuration.setProperty( AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, TenantIdentifierResolver.class.getName() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	// Add your tests, using standard JUnit.
	@Test
	public void testMergeWithTenantId() {
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		TestEntity entity = new TestEntity();
		entity.name = "test";
		s.persist(entity);
		tx.commit();
		s.close();
		s = openSession();
		tx = s.beginTransaction();
		entity = s.find(TestEntity.class, entity.id);
		entity.name = "test2";
		tx.commit(); // generated update SQL doesn't contains `where tenant = ?`
		s.close();
	}

	@Test
	public void testRemoveWithTenantId() {
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		TestEntity entity = new TestEntity();
		entity.name = "test";
		s.persist(entity);
		tx.commit();
		s.close();
		s = openSession();
		tx = s.beginTransaction();
		entity = s.find(TestEntity.class, entity.id);
		s.remove(entity);
		tx.commit(); // generated delete SQL doesn't contains `where tenant = ?`
		s.close();
	}

	@Entity(name = "TestEntity")
	static class TestEntity {
		@Id
		@GeneratedValue
		Long id;

		String name;

		@TenantId
		String tenant;
	}

	public static class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

		@Override
		public String resolveCurrentTenantIdentifier() {
			return "test";
		}

		@Override
		public boolean validateExistingCurrentSessions() {
			return false;
		}

		@Override
		public boolean isRoot(String tenantId) {
			return "root".equals(tenantId);
		}
	}
}
