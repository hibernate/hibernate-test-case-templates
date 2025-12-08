package org.hibernate.bugs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

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

    Parent parent = new Parent();
    entityManager.persist(parent);

    Child child = new Child().setParent(parent);
    entityManager.persist(child);

    entityManager.getTransaction().commit();
    entityManager.close();

    // LOAD ------------------------------------------------------------------
    EntityManager entityManager2 = entityManagerFactory.createEntityManager();
    entityManager2.getTransaction().begin();

    Child loaded = entityManager2.find(Child.class, child.getId());

    System.out.println();

    Parent loadedParent = loaded.getParent();
    System.out.println(loadedParent.getId());

    entityManager2.getTransaction().commit();

    entityManager2.close();
  }
}
