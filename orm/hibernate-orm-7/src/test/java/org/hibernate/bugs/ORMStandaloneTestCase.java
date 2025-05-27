package org.hibernate.bugs;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
class ORMStandaloneTestCase {

	private SessionFactory sf;

	@BeforeEach
	void setup() {
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
				// Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
				.applySetting( "hibernate.show_sql", "true" )
				.applySetting( "hibernate.format_sql", "true" )
				.applySetting( "hibernate.hbm2ddl.auto", "update" );

		Metadata metadata = new MetadataSources( srb.build() )
				// Add your entities here.
				//	.addAnnotatedClass( Foo.class )
				.buildMetadata();

		sf = metadata.buildSessionFactory();
	}

	// Add your tests, using standard JUnit 5:
	@Test
	void hhh123Test() throws Exception {

	}
}
