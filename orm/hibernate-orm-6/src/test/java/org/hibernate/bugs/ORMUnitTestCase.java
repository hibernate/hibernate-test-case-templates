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

import jakarta.persistence.criteria.CriteriaBuilder;
import org.assertj.core.api.Assertions;
import org.hibernate.bugs.application.InstrumentData;
import org.hibernate.bugs.application.InstrumentQueryService;
import org.hibernate.bugs.domain.model.*;
import org.hibernate.cfg.AvailableSettings;

import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.Setting;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
				// Add your entities here.
				Instrument.class,
				OrdinaryShare.class
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
	void hhh19542Test(SessionFactoryScope scope) throws Exception {
		scope.inTransaction( session -> {
			List<Instrument> instruments = session.createQuery("from Instrument ").list();
			System.out.println(instruments);
		} );
	}

	@Test
	void hhh19543Test(SessionFactoryScope scope) throws Exception {
		final InstrumentQueryService instrumentQueryService = new InstrumentQueryService();

		scope.inTransaction( session -> {
			Instrument instrument = new OrdinaryShare(
					new InstrumentCode("AVGO"),
					"Equity",
					"Broadcom",
					Arrays.asList(
							new InstrumentLine(
									new InstrumentLineKey("AVGO:XPAR"),
									new CurrencyCode("EUR"),
									"Broadcom Equity Paris"),
							new InstrumentLine(
									new InstrumentLineKey("AVGO:XMIL"),
									new CurrencyCode("EUR"),
									"Broadcom Equity Milan"),
							new InstrumentLine(
									new InstrumentLineKey("AVGO:NYSE"),
									new CurrencyCode("USD"),
									"Broadcom Equity New York Stock Exchange")));

			// Add any instruments you want here, purpose is to show aggregation by "code" (JoinOn)

			session.persist(instrument);

			List<InstrumentData> instruments = instrumentQueryService.allInstruments(session);

			assertThat( instruments ).isNotEmpty();
			assertThat( instruments.getFirst().lineKeys() ).isNotEmpty();
			assertThat( instruments.getFirst().lineKeys().size() == 3 ).isTrue();
		});
	}
}
