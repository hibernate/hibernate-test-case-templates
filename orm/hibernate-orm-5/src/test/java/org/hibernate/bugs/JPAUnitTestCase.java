package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.hibernate.bugs.constraint.violation.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

	@Rule
    public ExpectedException exception = ExpectedException.none();

	@Test
	public void hhh12923test() throws Exception {
		// Expecting a rollback exception here because Hibernate only realizes that we are violating a constraint while committing 
		// i.e. after inserting 2 identical rows. So it throws a RollbackException which is caused by ConstraintViolationException. 
		exception.expect(RollbackException.class);

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Event event1 = new Event("Concert", "Overland Park");
		Event event2 = new Event("Concert", "Overland Park");
		
		entityManager.persist(event1);
		System.out.println("Event 1 persisted.");
		entityManager.persist(event2);
		System.out.println("Event 2 persisted.");

		entityManager.getTransaction().commit();
		
		// The below message is never printed because the commit above is not successful.
		System.out.println("Committed");
		
		entityManager.close();
	}
}
