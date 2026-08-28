package org.hibernate.bugs;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<OurEntityPercentageStatus> criteriaQuery = criteriaBuilder.createQuery(OurEntityPercentageStatus.class);
		Root<OurEntity> root = criteriaQuery.from(OurEntity.class);
		criteriaQuery.groupBy(root.get("commonName"));

		OurStatus ourStatus = OurStatus.STATUS_1;

		// Get percentage of ourEntities in status STATUS_1. PSEUDOCODE: SUM(case when our_status == STATUS_1 then 1 else 0 end) / cast(Count(*) as decimal)) * 100  as percentage_out
		Selection<Double> percentageOutSelection = criteriaBuilder.prod(criteriaBuilder.quot(criteriaBuilder.sum(
			criteriaBuilder.selectCase(root.get("ourStatus"))
				.when(ourStatus.ordinal(), 1.0)
				.otherwise(0.0)
				.as(Double.class)), criteriaBuilder.count(root)), 100.0).as(Double.class).alias("percentage_out");

		//Get objects with dealerDatabaseId and percentage_out
		criteriaQuery.select(
			criteriaBuilder.construct(OurEntityPercentageStatus.class, root.get("commonName"), percentageOutSelection));
		CriteriaQuery<OurEntityPercentageStatus> finalQuery = criteriaQuery.multiselect(root.get("commonName"),
			percentageOutSelection);
		List<OurEntityPercentageStatus> resultList = entityManager.createQuery(finalQuery).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
