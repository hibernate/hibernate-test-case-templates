package org.hibernate.bugs;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.pojos.Catalogue;
import org.hibernate.pojos.HitCount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using the Java Persistence API.
 */
class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence
				.createEntityManagerFactory("templatePU");
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		entityManager.getTransaction().begin();

		HitCount event = new HitCount();

		entityManager.persist(event);

		Catalogue catalogue = new Catalogue();
		entityManager.persist(catalogue);

		Map<String, Object> args = new LinkedHashMap<String, Object>();

		args.put("newPartnumber", "23456");
		args.put("partnumber", "12345");

		String query = "update HitCount set partNumber=:newPartnumber where partNumber=:partnumber";
		// String query = "update HitCount hc set hc.partNumber=:newPartnumber
		// where hc.partNumber=:partnumber";
		// String query = "update HitCount hc set hc.partNumber=:newPartnumber
		// where fk(o.catalogue)=:partnumber";

		Query dbQuery = entityManager.createQuery(query);

		for (Map.Entry<String, Object> entry : args.entrySet()) {
			try {

				dbQuery.setParameter(entry.getKey(), entry.getValue());
			} catch (IllegalArgumentException e) {
				// ignored
			}

		}

		dbQuery.executeUpdate();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
