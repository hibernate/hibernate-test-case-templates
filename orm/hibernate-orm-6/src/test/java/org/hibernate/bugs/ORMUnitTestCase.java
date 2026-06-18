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

import jakarta.persistence.LockModeType;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.Setting;
import org.junit.jupiter.api.Test;

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
				Entity1.class,
				Entity2.class,
				Entity3.class
		},
		// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
		xmlMappings = {
				// "org/hibernate/test/Foo.hbm.xml",
				// "org/hibernate/test/Bar.hbm.xml"
		}
)
@ServiceRegistry(
		// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
		settings = {
				// For your own convenience to see generated queries:
				@Setting(name = AvailableSettings.SHOW_SQL, value = "true"),
				@Setting(name = AvailableSettings.FORMAT_SQL, value = "true"),
				// @Setting( name = AvailableSettings.GENERATE_STATISTICS, value = "true" ),

				// Add your own settings that are a part of your quarkus configuration:
				// @Setting( name = AvailableSettings.SOME_CONFIGURATION_PROPERTY, value = "SOME_VALUE" ),
		}
)
@SessionFactory
class ORMUnitTestCase {

	// Add your tests, using standard JUnit 5.
	@Test
	void hhh123Test(SessionFactoryScope scope) throws Exception {
		scope.inTransaction( session -> {
			Entity3 entity3 = new Entity3();
			entity3.setName("test");
			entity3.setSurname("tester");
			session.persist(entity3);
			Entity1 entity1 = new Entity1();
			entity1.setEntity3(entity3);
			session.persist(entity1);
			session.flush();
			session.clear();
			Long id = entity1.getId(); // or Long, depending on your ID type
			Entity1 entity1fromDb =  session.find(Entity1.class, id, LockModeType.PESSIMISTIC_WRITE);
			/*
			Hibernate:
				select
					e1_0.id,
					e2_0.id,
					e2_0.name,
					e2_0.surname,
					e1_0.name
				from
					Entity1 e1_0 with (updlock, holdlock, rowlock) -- this is fine it is not subselect
				left join
					(select
						*
					from
						Entity2 t
					where
						t.DTYPE='Entity3') e2_0 with (updlock, holdlock, rowlock)  -- here is the problem that fails on real MSSQL database
						on e2_0.id=e1_0.entity3_id
				where
					e1_0.id=?
			 */
			System.out.println("Gotcha!" + entity1fromDb.toString());
		} );
	}
}
