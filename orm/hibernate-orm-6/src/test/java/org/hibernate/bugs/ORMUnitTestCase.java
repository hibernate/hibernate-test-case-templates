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

import org.hibernate.Session;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.utility.DockerImageName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

	@Entity
	@Table(name = "MY_OBJ")
	private static class AaaTestEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MY_OBJ_SEQ")
		@Column(name = "ID")
		private Long primaryKey;
	}

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
				AaaTestEntity.class
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
		OracleContainer oracleContainer = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:18-slim"));
		oracleContainer.start();

		configuration.setProperty( "hibernate.connection.driver_class", oracleContainer.getDriverClassName());
		configuration.setProperty( "hibernate.dialect", "org.hibernate.dialect.OracleDialect");
		configuration.setProperty( "hibernate.connection.url", oracleContainer.getJdbcUrl());
		configuration.setProperty( "hibernate.connection.username", oracleContainer.getUsername());
		configuration.setProperty( "hibernate.connection.password", oracleContainer.getPassword());

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
		Session s = openSession();
		// check that my entity table exists
		s.createNativeQuery("select * from MY_OBJ").getResultList();

		// check that a non-existing table throws an exception
		Assert.assertThrows(RuntimeException.class, () -> {
			s.createNativeQuery("select * from MY_OBJ_NOT_EXISTING").getResultList();
		});


		// I would expect the HTE_* table to not exist, since I have a simple entity
		// however HTE_MY_OBJ exists and fails the test
		Assert.assertThrows(RuntimeException.class, () -> {
			s.createNativeQuery("select * from HTE_MY_OBJ").getResultList();
		});

		s.close();
	}
}
