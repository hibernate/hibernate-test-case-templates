package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.TypedQuery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
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
	public void hhh16824Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		EntityB entityB = new EntityB();
		entityB.setId(1l);
		entityB.setName("abc");
		entityB.setNumber(new BigDecimal(1l));
		entityManager.persist(entityB);
		// Do stuff...
		entityManager.getTransaction().commit();
		String queryStr = "SELECT entity FROM" +
				" org.hibernate.bugs.IEntityB entity WHERE entity.name=:str";
		TypedQuery<IEntityB> queryB = entityManager
				.createQuery(queryStr, IEntityB.class);
		queryB.setParameter("str", "abc");
		List<IEntityB> resultList = queryB.getResultList();
		Assert.assertEquals(1, resultList.size());

		String queryStrIn = "SELECT entity" +
				"        FROM org.hibernate.bugs.IEntityB entity" +
				"        WHERE entity.name IN :str";
		TypedQuery<IEntityB> queryIn = entityManager
				.createQuery(queryStrIn, IEntityB.class);
		queryIn.setParameter("str", Collections.singletonList("abc"));
		List<IEntityB> resultListIn = queryIn.getResultList();
		Assert.assertEquals(1, resultListIn.size());

		String queryStrMax = "SELECT MAX(entity.number)" +
				"        FROM org.hibernate.bugs.IEntityB entity" +
				"        WHERE entity.id=:myId";
		TypedQuery<Integer> query = entityManager
				.createQuery(queryStrMax, Integer.class);
		query.setParameter("myId", 1l);
		List<Integer> resultListInt = query.getResultList();
		Assert.assertEquals(1, resultListInt.size());
		entityManager.close();
	}
}
