package org.hibernate.bugs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.After;
import org.junit.Assert;
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
	public void hhh17455Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Member member = new Member();
		member.setFirstName("John");
		member.setLastName("Doe");
		member.setNickName("JD");

		entityManager.persist(member);

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> personQuery = cb.createQuery(Member.class);
		Root<Member> root = personQuery.from(Member.class);

		// Other query related code...

		// Start from a new root
		Root<Member> newRoot = (Root<Member>) root.as(Member.class); // This line works in v5
		Predicate where = cb.equal(newRoot.get("firstName"), "John");
		personQuery.where(where);

		personQuery.where(where);
		List<Member> members = entityManager.createQuery(personQuery).setMaxResults(100).setFirstResult(0)
				.getResultList();

		Assert.assertTrue(members.size() == 1);

		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
