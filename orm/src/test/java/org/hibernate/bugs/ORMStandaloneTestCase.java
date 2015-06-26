package org.hibernate.bugs;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
public class ORMStandaloneTestCase {

	private Configuration cfg;

	private SessionFactory sf;

	@Before
	public void setup() {
		cfg = new Configuration();

		// Add your entities here.
		// cfg.addAnnotatedClass(Foo.class);

		// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
		cfg.setProperty("hibernate.show_sql", "true");
		cfg.setProperty("hibernate.format_sql", "true");
		cfg.setProperty("hibernate.hbm2ddl.auto", "update");

		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder();
		ServiceRegistry sr = srb.build();
		sf = cfg.buildSessionFactory(sr);
	}

	// Add your tests, using standard JUnit.

	@Test
	public void hhh123Test() throws Exception {

	}
}
