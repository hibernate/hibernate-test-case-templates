package org.hibernate.bugs;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger log = LogManager.getLogger(this.getClass().getName());

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
	public void notFoundIssue() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
        ChessGame game = entityManager.find(ChessGame.class, 1L);
        assertNotNull("Returned entity shouldn't be null", game);
        assertNull(game.getPlayerBlack(), "Broken foreign key reference with NotFoundAction.IGNORE should return null");
        try {
            game.getPlayerWhite();
            fail("Accessing a broken foreign key reference should throw an exception.");
        } catch (Exception e) {
            // Exception expected
        }

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
