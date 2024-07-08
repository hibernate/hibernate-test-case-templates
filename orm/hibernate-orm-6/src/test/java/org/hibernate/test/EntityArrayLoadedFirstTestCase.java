package org.hibernate.test;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.entities.EntityArray;
import org.hibernate.entities.EntityJSON;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
public class EntityArrayLoadedFirstTestCase {

	private Metadata metadata;
	private SessionFactory sf;

	@Before
	public void setup() {
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
			// Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
			.applySetting( "hibernate.show_sql", "true" )
			.applySetting( "hibernate.format_sql", "true" )
			.applySetting( "hibernate.hbm2ddl.auto", "update" );

		metadata = new MetadataSources( srb.build() )
		// Add your entities here.
			.addAnnotatedClass( EntityArray.class )
			.addAnnotatedClass( EntityJSON.class )
			.buildMetadata();

		sf = metadata.buildSessionFactory();
	}

	// Add your tests, using standard JUnit.
	@Test
	public void hhh17680TestSucceedsForListString() throws Exception {
		var entityArray_listString = metadata.getEntityBinding(EntityArray.class.getName()).getProperty("listString").getType();
		var entityJSON_listString = metadata.getEntityBinding(EntityJSON.class.getName()).getProperty("listString").getType();
		assert entityArray_listString != entityJSON_listString;
	}

	@Test
	public void hhh17680TestSucceedsForListInteger() throws Exception {
		var entityArray_listInteger = metadata.getEntityBinding(EntityArray.class.getName()).getProperty("listInteger").getType();
		var entityJSON_listInteger = metadata.getEntityBinding(EntityJSON.class.getName()).getProperty("listInteger").getType();
		assert entityArray_listInteger != entityJSON_listInteger;
	}

}
