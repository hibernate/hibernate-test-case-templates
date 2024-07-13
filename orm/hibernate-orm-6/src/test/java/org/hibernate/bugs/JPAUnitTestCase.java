package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.TypedQuery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	public void hhh18353Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		/*
			You can see first assertion will go through but second will fail.
			Only different in queries is that one casts array elements explicitly,
			while other tries to infer element types and assure all elements has are
			of same type.
		 */

		Author author1 = entityManager.merge(new Author());
		Author author2 = entityManager.merge(new Author());
		Book book = entityManager.merge(new Book().setMainAuthor(author1).setContributedAuthor(author2));

		Set<Long> expectedResults = Set.of(author1.getId(), author2.getId());

		String castedQueryStr = "SELECT ARRAY(CAST(b.mainAuthor.id as Long), CAST(b.contributedAuthor.id as Long)) FROM Book b WHERE b = :book";
		TypedQuery<Long[]> castedQuery = entityManager.createQuery(castedQueryStr, Long[].class);
		castedQuery.setParameter("book", book);
		Long[] castedResults = castedQuery.getSingleResult();

		Assert.assertEquals(
			"Expect found ids to be ones that we inserted into database",
			expectedResults,
			Set.of(castedResults)
		);

		String nonCastedQueryStr = "SELECT ARRAY(b.mainAuthor.id, b.contributedAuthor.id) FROM Book b WHERE b = :book";
		TypedQuery<Long[]> nonCastedQuery = entityManager.createQuery(nonCastedQueryStr, Long[].class);
		nonCastedQuery.setParameter("book", book);
		Long[] nonCastedResults = nonCastedQuery.getSingleResult();

		Assert.assertEquals(
			"Expect found ids to be ones that we inserted into database",
			expectedResults,
			Set.of(nonCastedResults)
		);

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
