package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	/**
	 * Query without embeddedId
	 * @throws Exception
	 */
	@Test
	public void hhh16266NoEmbeddedIdTest() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// passes
		Query query = entityManager.createQuery(
				"SELECT COUNT(*) FROM QueryEntity u1, Visualization u2 WHERE u1.id IN (SELECT id from ModelEntity WHERE id=u2.queryId)");
		assertNotNull(query);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	/**
	 * Simple count query using embeddedId
	 * @throws Exception
	 */
	@Test
	public void hhh16266EmbeddedIdCountQueryTest() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// passes
		Query query = entityManager.createQuery("SELECT COUNT(*) from QueryUser WHERE id.queryId=?1 AND id.userId=?2");
		assertNotNull(query);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	/**
	 * EmbeddedId in the SubQuery section
	 * @throws Exception
	 */
	@Test
	public void hhh16266EmbeddedIdCountQueryWithSubQueryTest() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// passes
		Query query = entityManager.createQuery(
				"SELECT COUNT(*) from QueryEntity u1, Visualization u2 WHERE u1.id IN (SELECT id.queryId from QueryUser WHERE id.userId=?1)");
		assertNotNull(query);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	/**
	 * EmbeddedId in the SubQuery section with relation between the outer and
	 * the inner query
	 * 
	 * This query breaks
	 * 
	 * @throws Exception
	 */
	@Test
	public void hhh16266EmbeddedIdCountQueryWithSubQueryRelationshipTest() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// breaks and InterpretationException is thrown - Multiple from-elements expose unqualified attribute : id
		Query query = entityManager.createQuery(
				"SELECT COUNT(*) from QueryEntity u1, Visualization u2 WHERE u1.id IN (SELECT id.queryId from QueryUser WHERE id.queryId = u2.queryId AND id.userId=?1)");
		assertNotNull(query);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
