package org.hibernate.bugs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.bugs.model.Location;
import org.hibernate.bugs.model.Point;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

	private static final Long LOCATION_ID = 123412L;
	private static final String DOLLAR_QUOTE = "$asdas$";


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

		final Location location = entityManager.find(Location.class, LOCATION_ID);
		Assertions.assertEquals(LOCATION_ID, location.getId());
		Assertions.assertNotNull(location.getPoint());

		entityManager.getTransaction().commit();
		entityManager.close();;
	}

	private String createNativeQuery() throws JsonProcessingException {
		final String nativeQuery = "INSERT INTO location (id, point) " +
				"VALUES (%s, %s) " +
				"ON CONFLICT DO NOTHING;";

		final String dollarQuotedGeoJsonValue = makeDollarQuotedPoint();
		final String dollarQuotedId = makeDollarQuotedId();
		return String.format(nativeQuery, dollarQuotedId, dollarQuotedGeoJsonValue);
	}

	private String makeDollarQuotedPoint() throws JsonProcessingException {
		final String postgisWrapper = "ST_SetSRID(ST_GeomFromGeoJSON(%s%s%s), 4326)";
		return String.format(postgisWrapper, DOLLAR_QUOTE, makePoint(), DOLLAR_QUOTE);
	}

	private String makeDollarQuotedId() {
		return DOLLAR_QUOTE.concat(String.valueOf(LOCATION_ID)).concat(DOLLAR_QUOTE);
	}

	private String makePoint() throws JsonProcessingException {
		Point point = new Point(List.of(30.5, 50.4));
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(point);
	}

}
