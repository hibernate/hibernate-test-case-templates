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

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
//				Foo.class,
//				Bar.class
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

		configuration.addAnnotatedClass( Flour.class );
		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	@Test
	public void deleteWithEntityName() {
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		// Throws java.lang.IllegalArgumentException: Unknown entity: Flour
		session.delete( Flour.ENTITY_NAME, 1 );
		tx.commit();
		s.close();
	}

	@Test
	public void deleteNonExistingObject() {
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		// Throws javax.persistence.PersistenceException: org.hibernate.property.access.spi.PropertyAccessException: Error accessing field [private java.lang.Integer org.hibernate.bugs.ORMUnitTestCase$Flour.id] by reflection for persistent property [org.hibernate.bugs.ORMUnitTestCase$Flour#id] : 1
		session.delete( Flour.class.getName(), 1 );
		tx.commit();
		s.close();
	}

	@Test
	public void deleteExistingObject() {
		final Flour almond = new Flour( 1, "Almond", "made from ground almonds.", "Gluten free" );

		{
			Session s = openSession();
			Transaction tx = s.beginTransaction();
			session.persist( almond );
			tx.commit();
			s.close();
		}
		{
			Session s = openSession();
			Transaction tx = s.beginTransaction();
			// Throws javax.persistence.PersistenceException: org.hibernate.property.access.spi.PropertyAccessException: Error accessing field [private java.lang.Integer org.hibernate.bugs.ORMUnitTestCase$Flour.id] by reflection for persistent property [org.hibernate.bugs.ORMUnitTestCase$Flour#id] : 1
			session.delete( Flour.class.getName(), 1 );
			tx.commit();
			s.close();
		}
	}

	@Entity(name = "Flour")
	@Table(name = Flour.ENTITY_NAME)
	public static class Flour {
		public static final String ENTITY_NAME = "Flour";
		@Id
		private Integer id;
		private String name;
		private String description;
		private String type;

		public Flour() {
		}

		public Flour(Integer id, String name, String description, String type) {
			this.id = id;
			this.name = name;
			this.description = description;
			this.type = type;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return name;
		}

		@Override
		public boolean equals(Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			Flour flour = (Flour) o;
			return Objects.equals( name, flour.name ) &&
					Objects.equals( description, flour.description ) &&
					Objects.equals( type, flour.type );
		}

		@Override
		public int hashCode() {
			return Objects.hash( name, description, type );
		}
	}
}
