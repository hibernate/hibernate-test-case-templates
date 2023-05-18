package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.Query;
import org.hibernate.EntityD;
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
	public void hhh16634Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Query nativeQuery = entityManager.createNativeQuery("UPDATE my_seq set my_seq_val=50");
		int i = nativeQuery.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.getTransaction().begin();
		assert i>0;

		EntityD entityD = new EntityD();
		entityD.setName("abc");
		EntityD merge = entityManager.merge(entityD);
		System.out.println("*********** ID="+merge.getId());
		assert merge.getId()>50;
		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.getTransaction().begin();
		EntityD entityD1 = new EntityD();
		entityD1.setName("cde");
		EntityD merge1 = entityManager.merge(entityD1);
		System.out.println("*********** ID="+merge1.getId());
		assert merge1.getId()>50;
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
