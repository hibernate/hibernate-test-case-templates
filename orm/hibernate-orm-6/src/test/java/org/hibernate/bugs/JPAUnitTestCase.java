package org.hibernate.bugs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Date;

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
        Event event = new Event(new Date());
        entityManager.persist(event);

        NotificationInfo notificationInfo = new NotificationInfo(event.getId(), "test notification 11111");
        entityManager.persist(notificationInfo);

        entityManager.flush();
        entityManager.clear();

        NotificationInfo dbNotificationInfo = entityManager.createQuery(
                        "select n from NotificationInfo n where n.eventId = :event_id", NotificationInfo.class)
                .setParameter("event_id", event.getId())
                .getSingleResult();

        System.out.println("dbNotificationInfo ============>: " + dbNotificationInfo);

        // When adding quotation marks to the SQL in the @Formula annotation of the Event.info field,
        // the SQL will parse incorrectly
        Event dbEvent = entityManager.createQuery(
                        "select e from Event e", Event.class)
                .getSingleResult();

        System.out.println("Select Event ============>: " + dbEvent);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
