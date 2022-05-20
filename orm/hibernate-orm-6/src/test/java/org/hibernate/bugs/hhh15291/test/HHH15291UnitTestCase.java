package org.hibernate.bugs.hhh15291.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

import org.hibernate.bugs.hhh15291.model.HHH15291Entity;
import org.hibernate.bugs.hhh15291.model.HHH15291Entity_;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using the Java Persistence API.
 */
public class HHH15291UnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("hhh15291PU");
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh15291JPQL1Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			TypedQuery<HHH15291Entity> query = entityManager.createQuery("" + "SELECT t FROM HHH15291Entity t "
					+ "WHERE t.itemString2 = " + "COALESCE (t.itemString1, ?1)", HHH15291Entity.class);
			query.setParameter(1, "Sample");
			query.getResultList();
		} catch (Throwable t) {
			throw t;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	@Test
	public void hhh15291JPQL2Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			TypedQuery<String> query2 = entityManager.createQuery(
					"" + "SELECT COALESCE (t.itemString2, ?1) FROM HHH15291Entity t ORDER BY t.itemInteger1 ASC",
					String.class);
			query2.setParameter(1, "Sample");
			query2.getResultList();
		} catch (Throwable t) {
			throw t;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	@Test
	public void hhh15291Criteria1Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<HHH15291Entity> cquery = cb.createQuery(HHH15291Entity.class);
			Root<HHH15291Entity> root = cquery.from(HHH15291Entity.class);
			cquery.select(root);

			ParameterExpression<String> checkParam1 = cb.parameter(String.class);
			Expression<String> coalesce = cb.coalesce(root.get(HHH15291Entity_.itemString1), checkParam1);
			cquery.where(cb.equal(root.get(HHH15291Entity_.itemString2), coalesce));

			TypedQuery<HHH15291Entity> query = entityManager.createQuery(cquery);
			query.setParameter(checkParam1, "Sample");
			query.getResultList();
		} catch (Throwable t) {
			throw t;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}

	@Test
	public void hhh15291Criteria2Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<HHH15291Entity> cquery = cb.createQuery(HHH15291Entity.class);
			Root<HHH15291Entity> root = cquery.from(HHH15291Entity.class);
			cquery.select(root);

			ParameterExpression<String> checkParam1 = cb.parameter(String.class);
			Expression<String> coalesce = cb.coalesce(root.get(HHH15291Entity_.itemString1), checkParam1);
			cquery.where(cb.equal(root.get(HHH15291Entity_.itemString2), coalesce));

			TypedQuery<HHH15291Entity> query = entityManager.createQuery(cquery);
			query.setParameter(checkParam1, "Sample");
			query.getResultList();
		} catch (Throwable t) {
			throw t;
		} finally {
			if (entityManager.isOpen()) {
				entityManager.clear();
				entityManager.close();
			}
		}
	}
}
