package org.hibernate.bugs;

import org.hibernate.bugs.entity.PrimaryObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase
{

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init()
	{
		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
	}

	@After
	public void destroy()
	{
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PrimaryObject> query = builder.createQuery(PrimaryObject.class);
		Root<PrimaryObject> root = query.from(PrimaryObject.class);

		query.where(
			builder.equal(
				root.get("child").get("secondaryObjects").get("data"), "stuff"));

		entityManager.createQuery(query).getSingleResult();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
