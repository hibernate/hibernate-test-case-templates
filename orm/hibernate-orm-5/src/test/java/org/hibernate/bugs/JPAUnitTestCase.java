package org.hibernate.bugs;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;

import org.hibernate.engine.spi.SessionImplementor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		final EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		final Connection connection = entityManager.unwrap(SessionImplementor.class).connection();

		try (  final Statement stmnt = connection.createStatement();
		       final ResultSet rs = stmnt.executeQuery("select sqlDate, localDate  from Entity")
		) {
			final ResultSetMetaData metadata = rs.getMetaData();

			Assert.assertEquals ( "sqlDate is not DATE", "DATE", metadata.getColumnTypeName(1) );
			Assert.assertEquals ( "localDate is not DATE", "DATE", metadata.getColumnTypeName(2) );
		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
