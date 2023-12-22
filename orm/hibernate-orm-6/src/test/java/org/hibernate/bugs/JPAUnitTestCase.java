package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.annotations.QueryHints;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
	public void hhh17423Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		MainEntity mainEntity = new MainEntity();
		mainEntity.setId(1l);
		mainEntity.setMainName("main entity name");
		mainEntity.setSomeFlag(false);
		entityManager.persist(mainEntity);
		entityManager.getTransaction().commit();
		entityManager.clear();

		//Load entity to the 2nd lvl cache & to 1lvl
		//also query results will be cached to 2nd level cache

		TypedQuery<MainEntity> query = createQuery(entityManager, "SELECT e FROM MainEntity e WHERE e.mainName like '%main%'");

		//seems this transaction thing here is important, without it the last query somehow thinks it can't find query in query cache
		entityManager.getTransaction().begin();
		List<MainEntity> list = query.getResultList();
		entityManager.getTransaction().commit();

		//load same entity but with different query, entity is in 1lvl cache and will be returned from there
		//!!!BUT!!! 2lvl cache will be populated with id only
		TypedQuery<MainEntity> query1 = createQuery(entityManager,"SELECT e FROM MainEntity e WHERE e.someFlag=false");
		List<MainEntity> list1 = query1.getResultList();

		//now clear lvl1 cache
		entityManager.clear();

		//do the same query again, expect the full entity will be seeded from the 2nd level cache
		//but in 2nd lvl cache entity has only id populated, so entity with only id will be returned
		TypedQuery<MainEntity> query2 = createQuery(entityManager, "SELECT e FROM MainEntity e WHERE e.someFlag=false");
		List<MainEntity> list2 = query2.getResultList();
		entityManager.close();

		Assert.assertEquals(list1.size(),list2.size());
		Assert.assertEquals(list1.get(0).getId(), list2.get(0).getId());
		Assert.assertEquals(list1.get(0).getMainName(), list2.get(0).getMainName());
		Assert.assertEquals(list1.get(0).getSomeFlag(), list2.get(0).getSomeFlag());
	}

	private static TypedQuery<MainEntity> createQuery(EntityManager entityManager, String hql) {
		TypedQuery<MainEntity> query = entityManager.createQuery(hql, MainEntity.class);
		query.setHint(QueryHints.CACHEABLE, true);
		query.setHint(QueryHints.CACHE_REGION, "some-query");
		return query;
	}
}
