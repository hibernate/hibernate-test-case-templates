package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Root;
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
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EntityA> cq = cb.createQuery(EntityA.class);
		final Root<EntityA> entityARoot = cq.from(EntityA.class);
		final ListJoin<EntityA, EntityB> entityBJoin = entityARoot.join(EntityA_.entitiesB);

		cq.where(cb.equal(
				entityBJoin.get(EntityB_.key).get(EntityBKey_.id),
				"foo"
		));

		cq.select(entityARoot);

		final var result = entityManager.createQuery(cq).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
