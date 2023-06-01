package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.After;
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
	public void hhhEnumCompareWithObject() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Event event1 = new Event( EventType.EV1 );
		entityManager.persist( event1 );

		Event event2 = new Event( EventType.EV2 );
		entityManager.persist( event2 );

		Event dbEventOk = entityManager.createQuery(
            "select e " +
            "from Event e where e.type = :type", Event.class)
			.setParameter("type", EventType.EV1)
        .getSingleResult();

		
		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void hhhEnumCompareWithTypedConstant() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Event event1 = new Event( EventType.EV1 );
		entityManager.persist( event1 );

		Event event2 = new Event( EventType.EV2 );
		entityManager.persist( event2 );
		
		// this will fail
		Event dbEventFail = entityManager.createQuery(
            "select e " +
            "from Event e where e.type = :type and :type = org.hibernate.bugs.EventType.EV1", Event.class)
			.setParameter("type", EventType.EV1)
        .getSingleResult();

		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void hhhEnumCompareWithStringConstant() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Event event1 = new Event( EventType.EV1 );
		entityManager.persist( event1 );

		Event event2 = new Event( EventType.EV2 );
		entityManager.persist( event2 );
		
		// this will fail
		Event dbEventFail = entityManager.createQuery(
            "select e " +
            "from Event e where e.type = :type and :type = 'EV1'", Event.class)
			.setParameter("type", EventType.EV1)
        .getSingleResult();

		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}


	@Test
	public void hhhEnumCompareWithTypedConstantDifferentOrder() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Event event1 = new Event( EventType.EV1 );
		entityManager.persist( event1 );

		Event event2 = new Event( EventType.EV2 );
		entityManager.persist( event2 );
		
		// this will fail
		Event dbEventFail = entityManager.createQuery(
            "select e " +
            "from Event e where :type =  org.hibernate.bugs.EventType.EV1 and e.type = :type ", Event.class)
			.setParameter("type", EventType.EV1)
        .getSingleResult();

		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void hhhEnumCompareWithStringConstantDifferentOrder() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Event event1 = new Event( EventType.EV1 );
		entityManager.persist( event1 );

		Event event2 = new Event( EventType.EV2 );
		entityManager.persist( event2 );
		
		// this will fail
		Event dbEventFail = entityManager.createQuery(
            "select e " +
            "from Event e where :type = 'EV1' and e.type = :type ", Event.class)
			.setParameter("type", EventType.EV1)
        .getSingleResult();

		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void hhhEnumCompareWithMultipleStringConstant() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Event event1 = new Event( EventType.EV1 );
		entityManager.persist( event1 );

		Event event2 = new Event( EventType.EV2 );
		entityManager.persist( event2 );
		
		// this will fail
		Event dbEventFail = entityManager.createQuery(
            "select e " +
            "from Event e where  :type = 'EV1' or :type = 'EV2' and e.type = :type ", Event.class)
			.setParameter("type", EventType.EV1)
        .getSingleResult();

		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
