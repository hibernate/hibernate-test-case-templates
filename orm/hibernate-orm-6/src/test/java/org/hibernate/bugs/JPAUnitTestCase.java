package org.hibernate.bugs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

  private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

    Query query = entityManager.createQuery("SELECT t FROM Tag t WHERE (:attributeFilter IS NULL OR FUNCTION('array_position', t.attributes, CAST(:attributeFilter AS String)) IS NOT NULL)");
    query.setParameter("attributeFilter", "ATTR");
    List a = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
