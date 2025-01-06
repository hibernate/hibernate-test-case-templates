package org.hibernate.bugs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.bugs.model.Point;
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
	void hhh18956Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		final String query = createNativeQuery();
		entityManager.createNativeQuery(query).executeUpdate();
		entityManager.getTransaction().commit();

		entityManager.close();;
	}

	private String createNativeQuery() throws JsonProcessingException {
		final String nativeQuery = "INSERT INTO location (point) " +
				"VALUES (%s) " +
				"ON CONFLICT DO NOTHING;";

		final String dollarQuotedGeoJsonValue = makeDollarQuotedValue();
		return String.format(nativeQuery, dollarQuotedGeoJsonValue);
	}

	private String makeDollarQuotedValue() throws JsonProcessingException {
		final String dollarQuote = "$asdas$";
		final String postgisWrapper = "ST_SetSRID(ST_GeomFromGeoJSON(%s%s%s), 4326)";
		return String.format(postgisWrapper, dollarQuote, makePoint(), dollarQuote);
	}

	private String makePoint() throws JsonProcessingException {
		Point point = new Point(List.of(30.5, 50.4));
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(point);
	}

}
