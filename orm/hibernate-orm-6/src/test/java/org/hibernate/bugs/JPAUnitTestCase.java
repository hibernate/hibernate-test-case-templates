package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.Query;
import org.hibernate.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
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
	public void HHH16582Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		EntityA a = new EntityA();
		a.setName("abc");
		a.setId(1l);
		EntityB b = new EntityB();
		b.setSomething("cde");
		b.setId(1l);
		b.setA(a);
		IEntityC c = new EntityC();
		c.setName("kkk");
		c.setId(1l);
		c.setB(b);
		HashSet<IEntityC> cs = new HashSet<>();
		cs.add(c);
		HashSet<IEntityB> bs = new HashSet<>();
		bs.add(b);
		a.setBs(bs);
		b.setCs(cs);
		entityManager.persist(a);
		entityManager.persist(b);
		entityManager.persist(c);
		Query getEntityA = entityManager.createNamedQuery("getEntityA");
		entityManager.getTransaction().commit();
		entityManager.getTransaction().begin();
		IEntityC entityC = entityManager.find(EntityC.class, 1);
		getEntityA.setParameter("adminUser", entityC);
		Long result = (Long) getEntityA.getSingleResult();
		Assert.assertEquals("expect 1 result", 1l, result.longValue());
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
