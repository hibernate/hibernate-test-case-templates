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
 * This test demonstrates HHH-13360.
 */
public class AttributeConverterWithSQLDateUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	@Test
	public void hhh13360Test() throws Exception {
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
