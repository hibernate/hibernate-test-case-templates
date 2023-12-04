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

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.bugs.modal.OrganizationEntity;
import org.hibernate.bugs.modal.OrganizationMemberEntity;
import org.hibernate.bugs.modal.OrganizationMemberPO;
import org.hibernate.bugs.modal.UserEntity;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;

import java.util.List;

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
				UserEntity.class,
				OrganizationEntity.class,
				OrganizationMemberEntity.class
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
		configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
		configuration.setProperty( "hibernate.hbm2ddl.auto", "create" );
		configuration.setProperty(JdbcSettings.DIALECT, "org.hibernate.dialect.H2Dialect" );
	}

	// Add your tests, using standard JUnit.
	@Test
	public void hhh17511Test() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		// Do stuff...
		TypedQuery<OrganizationMemberPO> query = s.createQuery("SELECT NEW org.hibernate.bugs.modal.OrganizationMemberPO(om.id, om.userId, om.organizationId,organization.name,organization.displayPath,om.primary) FROM OrganizationMemberEntity om INNER JOIN OrganizationEntity organization ON organization.id = om.organizationId WHERE om.userId =:userId", OrganizationMemberPO.class);
		query.setParameter("userId", 1L);
        List<OrganizationMemberPO> list=query.getResultList();
		tx.commit();
		s.close();
	}
}
