package org.hibernate.test;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.entities.EntityArray;
import org.hibernate.entities.EntityJSON;
import org.hibernate.entities.EntityPlain;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.type.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
public class EntityJsonLoadedFirstTestCase {

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
			.addAnnotatedClass( EntityJSON.class )
			.addAnnotatedClass( EntityArray.class )
			.addAnnotatedClass( EntityPlain.class )
			.buildMetadata();

		sf = metadata.buildSessionFactory();
	}

	// Add your tests, using standard JUnit.
	@Test
	public void hhh17680Test() throws Exception {
		var modelEntityArray = metadata.getEntityBinding(EntityArray.class.getName());
		var modelEntityJson = metadata.getEntityBinding(EntityJSON.class.getName());
		var modelEntityPlain = metadata.getEntityBinding(EntityPlain.class.getName());

		// Entities with JdbcTypes JSON and ARRAY should have different types
		Assert.assertNotEquals(
				"Entity with JdbcType ARRAY and entity with JdbcType JSON should have the different types",
				getPropertyType(modelEntityArray, "listString"),
				getPropertyType(modelEntityJson, "listString")
		);
		Assert.assertNotEquals(
				"Entity with JdbcType ARRAY and entity with JdbcType JSON should have the different types",
				getPropertyType(modelEntityArray, "listInteger"),
				getPropertyType(modelEntityJson, "listInteger")
		);

		//
		Assert.assertEquals(
				"Entity with JdbcType ARRAY and entity without JdbcType should have the same types",
				getPropertyType(modelEntityArray, "listString"),
				getPropertyType(modelEntityPlain, "listString")
		);
		Assert.assertEquals(
				"Entity with JdbcType ARRAY and entity without JdbcType should have the same types",
				getPropertyType(modelEntityArray, "listInteger"),
				getPropertyType(modelEntityPlain, "listInteger")
		);
	}

	private Type getPropertyType(PersistentClass model, String property) {
		return model.getProperty(property).getType();
	}
}
