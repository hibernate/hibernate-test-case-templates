package org.hibernate.bugs;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
	public void hhh15174Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

        ZonedDateTime zonedDateTime = ZonedDateTime.of(2022, 4, 6, 12, 0, 0, 0, ZoneId.of("UTC+4"));
        OffsetDateTime offsetDateTime = OffsetDateTime.of(2022, 4, 6, 12, 0, 0, 0, ZoneOffset.ofHours(4));

        TimeZoneStorageExample e = new TimeZoneStorageExample();
        e.setZonedDateTimeLocal(zonedDateTime);
        e.setZonedDateTimeUtc(zonedDateTime);
        e.setOffsetDateTimeLocal(offsetDateTime);
        e.setOffsetDateTimeUtc(offsetDateTime);
        entityManager.persist(e);

        Object[] timestamps = (Object[]) entityManager.createNativeQuery("SELECT offsetDateTimeLocal, zonedDateTimeLocal, offsetDateTimeUtc, zonedDateTimeUtc FROM TimeZoneStorageExample WHERE id = :id")
                                                      .setParameter("id", e.getId())
                                                      .getSingleResult();

        assertTrue("offsetDateTimeLocal should be 2022-04-06 10:00:00.0", "2022-04-06 10:00:00.0".equals(((Timestamp)timestamps[0]).toString()));
        assertTrue("zonedDateTimeLocal should be 2022-04-06 10:00:00.0", "2022-04-06 10:00:00.0".equals(((Timestamp)timestamps[1]).toString()));
        assertTrue("offsetDateTimeUtc should be 2022-04-06 08:00:00.0", "2022-04-06 08:00:00.0".equals(((Timestamp)timestamps[2]).toString()));
        assertTrue("zonedDateTimeUtc should be 2022-04-06 08:00:00.0", "2022-04-06 08:00:00.0".equals(((Timestamp)timestamps[3]).toString()));
		
        entityManager.getTransaction().commit();
		entityManager.close();
	}
}
