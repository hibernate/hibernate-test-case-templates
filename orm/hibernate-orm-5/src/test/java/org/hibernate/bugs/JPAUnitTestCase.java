package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.org.entity.E_Cart;
import com.org.entity.E_CartItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	Long id = null;
	EntityManager entityManager = null;
	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
		entityManager = entityManagerFactory.createEntityManager();

	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh1Test() throws Exception {
		entityManager.getTransaction().begin();
		// Do stuff...
		E_Cart cart = new E_Cart();
		entityManager.persist(cart);

		E_CartItem ci = new E_CartItem();
		ci.setVersion("one");
		ci.setCart(cart);
		entityManager.persist(ci);

		entityManager.getTransaction().commit();
	}

}
