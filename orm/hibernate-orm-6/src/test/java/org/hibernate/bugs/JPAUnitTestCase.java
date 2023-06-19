package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.After;
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
	public void hhh16818est() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		EntityC entityC =  new EntityC();
		entityC.setId(1l);
		entityC.setcName('A');

		entityManager.persist(entityC);
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("UPDATE EntityC c SET c.cName='J'");
		query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.clear();
		EntityC entityC1 = entityManager.find(EntityC.class, 1l);
		assert entityC1.getcName().equals('J');

		entityManager.close();
	}
}
