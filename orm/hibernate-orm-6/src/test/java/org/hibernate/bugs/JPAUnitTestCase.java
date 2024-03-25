package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
	public void HHH16581Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		EntityA a = new EntityA();
		a.setName("aaa");
		a.setId(1l);
		entityManager.persist(a);
		EntityB b = new EntityB();
		b.setName("bbb");
		b.setId(1l);
		entityManager.persist(b);
		entityManager.getTransaction().commit();
		entityManager.getTransaction();
		Filter filterA = entityManager.unwrap(Session.class).enableFilter("filterA");
		filterA.setParameter("name", "bbb");

		List<EntityB> bs = entityManager.createQuery("select b from EntityB b", EntityB.class)
				.getResultList();
		Assert.assertTrue("there should be results", bs.size()>0);
		entityManager.close();
	}
}
