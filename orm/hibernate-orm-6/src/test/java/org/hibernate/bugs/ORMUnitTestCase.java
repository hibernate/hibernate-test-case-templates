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

import jakarta.persistence.*;
import org.hibernate.cfg.AvailableSettings;

import org.hibernate.testing.jdbc.SQLStatementInspector;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.Setting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 * <p>
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
@DomainModel(
		annotatedClasses = {
				ORMUnitTestCase.Parent.class,
				ORMUnitTestCase.Child.class
		}
)
@ServiceRegistry(
		// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
		settings = {
				// For your own convenience to see generated queries:
				@Setting(name = AvailableSettings.SHOW_SQL, value = "true"),
				@Setting(name = AvailableSettings.FORMAT_SQL, value = "true"),
				@Setting(name = AvailableSettings.GENERATE_STATISTICS, value = "true"),

				// Add your own settings that are a part of your quarkus configuration:
				// @Setting( name = AvailableSettings.SOME_CONFIGURATION_PROPERTY, value = "SOME_VALUE" ),
		}
)
@SessionFactory
class ORMUnitTestCase {

	@BeforeEach
	public void setUp(SessionFactoryScope scope) {
		scope.inTransaction(
				session -> {
					Child v = new Child();
					v.setId("1");
					v.setExternalId("1");
					Parent tc = new Parent();
					tc.setId(2L);
					tc.setChild(v);
					tc.setSomeFilter("some-value");

					session.persist(v);
					session.persist(tc);
				});
	}

	@AfterEach
	public void tearDown(SessionFactoryScope scope) {
		scope.inTransaction(
				session -> {
					session.createQuery("delete from Parent ").executeUpdate();
					session.createQuery("delete from Child").executeUpdate();
				});
	}


	// Add your tests, using standard JUnit 5.
	@Test
	void hhh123Test(SessionFactoryScope scope) throws Exception {
		scope.inTransaction(session -> {
			SQLStatementInspector statementInspector = scope.getCollectingStatementInspector();
			statementInspector.clear();
			final String queryString = "SELECT p from Parent p LEFT JOIN FETCH p.child c WHERE (c.externalId = :providedId or c.id = :providedId) and p.someFilter = :filterValue";
			final Parent tc = session.createQuery(queryString, Parent.class)
					.setParameter("providedId", "1")
					.setParameter("filterValue", "some-value")
					.getSingleResult();

			assertNotNull(tc);

			statementInspector.assertNumberOfOccurrenceInQueryNoSpace(0, "c1_0\\.id", 3);
		});
	}

	@Entity(name = "Parent")
	@Table(name = "parents", indexes = {
			@Index(name = "child_id_idx", columnList = "child_id")
	})
	public static class Parent {

		@Id
		private Long id;

		@Column(name = "some_filter", columnDefinition = "VARCHAR")
		private String someFilter;

		@ManyToOne(cascade = {CascadeType.PERSIST})
		@JoinColumn(name = "child_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "children_fk"))
		private Child child;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSomeFilter() {
			return someFilter;
		}

		public void setSomeFilter(String someFilter) {
			this.someFilter = someFilter;
		}

		public Child getChild() {
			return child;
		}

		public void setChild(Child child) {
			this.child = child;
		}
	}

	@Entity(name = "Child")
	@Table(name = "children", indexes = {
			@Index(name = "external_id_idx", columnList = "external_id")
	})
	public static class Child {
		@Id
		@Column(name = "id", columnDefinition = "VARCHAR")
		private String id;

		@Column(name = "external_id", columnDefinition = "VARCHAR")
		private String externalId;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getExternalId() {
			return externalId;
		}

		public void setExternalId(String externalId) {
			this.externalId = externalId;
		}
	}
}
