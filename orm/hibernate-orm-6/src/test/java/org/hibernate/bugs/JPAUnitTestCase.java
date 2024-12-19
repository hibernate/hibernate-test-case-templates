package org.hibernate.bugs;

import jakarta.persistence.Tuple;
import org.hibernate.Session;
import org.hibernate.bugs.model.Person;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

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
		// Do stuff...

		final HibernateCriteriaBuilder cb = entityManager.unwrap(Session.class).getCriteriaBuilder();
		var wrapperQuery = cb.createTupleQuery();
		var subquery1 = wrapperQuery.subquery(Tuple.class);
		var root1 = subquery1.from(Person.class);
		subquery1.multiselect(
						root1.get("id").alias("id"),
						root1.get("firstName").alias("firstName"),
						root1.get("lastName").alias("lastName"),
						root1.get("email").alias("email"),
						root1.get("phone").alias("phone"),
						root1.get("address").alias("address"))
				.where(cb.equal(root1.get("address").get("city"), "NYC"));

		var subquery2 = wrapperQuery.subquery(Tuple.class);
		var root2 = subquery2.from(Person.class);
		subquery2.multiselect(
						root2.get("id").alias("id"),
						root2.get("firstName").alias("firstName"),
						root2.get("lastName").alias("lastName"),
						root2.get("email").alias("email"),
						root2.get("phone").alias("phone"),
						root2.get("address").alias("address"))
				.where(cb.like(root2.get("lastName"), "ba%"));

		var unionQuery = cb.union(subquery1, subquery2);
		var wrapperRoot = wrapperQuery.from(unionQuery);

		wrapperQuery.multiselect(
				wrapperRoot.get("id").alias("id"),
				wrapperRoot.get("email").alias("email"),
				wrapperRoot.get("address").alias("address")
		).orderBy(cb.desc(cb.literal(1)));

		List<Tuple> tuples = entityManager.createQuery(wrapperQuery).setMaxResults(5).getResultList();

		System.out.println(tuples);

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
