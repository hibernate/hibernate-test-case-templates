package org.hibernate.bugs;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.pojos.Catalogue;
import org.hibernate.pojos.HitCount;
import org.hibernate.pojos.HitCount_;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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

		Catalogue catalogue = new Catalogue();
		Catalogue catalogue1 = new Catalogue();
		catalogue1.setPartNumber("23456");

		HitCount hc = new HitCount();
		hc.setCatalogue(catalogue);

		entityManager.persist(hc);

		// Catalogue catalogue = new Catalogue();
		entityManager.persist(catalogue);
		entityManager.persist(catalogue1);

		entityManager.flush();

		Map<String, Object> args = new LinkedHashMap<String, Object>();

		args.put("newPartnumber", "23456");
		args.put("partnumber", "12345");

		// String query = "update HitCount set partNumber=:newPartnumber where
		// partNumber=:partnumber";
		// String query = "update HitCount set catalogue.id=:newPartnumber where
		// catalogue.id=:partnumber";
		// String query = "update HitCount hc set hc.partNumber=:newPartnumber
		// where fk(o.catalogue)=:partnumber";

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<HitCount> criteriaQuery = builder
				.createQuery(HitCount.class);
		Root<HitCount> root = criteriaQuery.from(HitCount.class);

		criteriaQuery.select(root);

		Predicate before = builder.equal(root.get(HitCount_.catalogue),
				catalogue1);

		org.hibernate.query.Query<HitCount> queryb = (org.hibernate.query.Query<HitCount>) entityManager
				.createQuery(criteriaQuery.where(before));

		HitCount obj = queryb.getSingleResultOrNull();
		assertNull(obj, "Should be null");

		String query = "update HitCount set catalogue.id=:newPartnumber where catalogue.id=:partnumber";

		Query dbQuery = entityManager.createQuery(query);

		for (Map.Entry<String, Object> entry : args.entrySet()) {
			try {

				dbQuery.setParameter(entry.getKey(), entry.getValue());
			} catch (IllegalArgumentException e) {
				// ignored
			}

		}

		dbQuery.executeUpdate();

		entityManager.flush();

		org.hibernate.query.Query<HitCount> querya = (org.hibernate.query.Query<HitCount>) entityManager
				.createQuery(criteriaQuery.where(before));

		HitCount objAfter = querya.getSingleResultOrNull();
		assertNotNull(objAfter, "Should not be null");

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
