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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.pojos.Catalogue;
import org.hibernate.pojos.HitCount;
import org.hibernate.pojos.HitCount_;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.Setting;
import org.junit.jupiter.api.Test;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using its built-in unit test framework. Although ORMStandaloneTestCase is
 * perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing
 * your reproducer using this method simplifies the process.
 * <p>
 * What's even better? Fork hibernate-orm itself, add your test case directly to
 * a module's unit tests, then submit it as a PR!
 */
@DomainModel(annotatedClasses = {
		// Add your entities here.
		Catalogue.class, HitCount.class },
		// If you use *.hbm.xml mappings, instead of annotations, add the
		// mappings here.
		xmlMappings = {
		// "org/hibernate/test/Foo.hbm.xml",
		// "org/hibernate/test/Bar.hbm.xml"
		})
@ServiceRegistry(
		// Add in any settings that are specific to your test. See
		// resources/hibernate.properties for the defaults.
		settings = {
				// For your own convenience to see generated queries:
				@Setting(name = AvailableSettings.SHOW_SQL, value = "true"),
				@Setting(name = AvailableSettings.FORMAT_SQL, value = "true"),
		// @Setting( name = AvailableSettings.GENERATE_STATISTICS, value =
		// "true" ),

		// Add your own settings that are a part of your quarkus configuration:
		// @Setting( name = AvailableSettings.SOME_CONFIGURATION_PROPERTY, value
		// = "SOME_VALUE" ),
		})
@SessionFactory
class ORMUnitTestCase {

	// Add your tests, using standard JUnit 5.
	@Test
	void hhh123Test(SessionFactoryScope scope) throws Exception {
		scope.inTransaction(session -> {
			// Do stuff...

			// Test for mariadb Server version: 10.11.6-MariaDB-0+deb12u1 -
			// Debian 12 see hibernate.properties

			Catalogue catalogue = new Catalogue();
			Catalogue catalogue1 = new Catalogue();
			catalogue1.setPartNumber("23456");

			HitCount hc = new HitCount();
			hc.setCatalogue(catalogue);

			session.persist(hc);

			// Catalogue catalogue = new Catalogue();
			session.persist(catalogue);
			session.persist(catalogue1);

			session.flush();

			Map<String, Object> args = new LinkedHashMap<String, Object>();

			args.put("newPartnumber", "23456");
			args.put("partnumber", "12345");

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<HitCount> criteriaQuery = builder
					.createQuery(HitCount.class);
			Root<HitCount> root = criteriaQuery.from(HitCount.class);

			criteriaQuery.select(root);

			Predicate before = builder.equal(root.get(HitCount_.catalogue),
					catalogue1);

			Query<HitCount> querybz = session
					.createQuery(criteriaQuery.where(before));

			HitCount obj = querybz.getSingleResultOrNull();
			assertNull(obj, "Should be null");

			// Verify test works, uncomment partNumber in HitCount pojo.
			// String query = "update HitCount set partNumber=:newPartnumber
			// where partNumber=:partnumber";

			String query = "update HitCount set catalogue.id=:newPartnumber where catalogue.id=:partnumber";

			MutationQuery dbQuery = session.createMutationQuery(query);

			for (Map.Entry<String, Object> entry : args.entrySet()) {
				try {

					dbQuery.setParameter(entry.getKey(), entry.getValue());
				} catch (IllegalArgumentException e) {
					// ignored
				}

			}

			dbQuery.executeUpdate();

			session.flush();
			session.clear();

			Query<HitCount> querya = session
					.createQuery(criteriaQuery.where(before));

			HitCount objAfter = querya.getSingleResultOrNull();
			assertNotNull(objAfter, "Should not be null");

			System.out.println(objAfter.getId());

		});
	}
}
