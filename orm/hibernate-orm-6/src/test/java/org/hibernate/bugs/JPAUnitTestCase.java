package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

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
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// Do stuff...

		Product p = new ProductImpl();
		p.setId(1l);
		SkuImpl sku = new SkuImpl();
		p.setDefaultSku(sku);
		p.setName("abc");
		sku.setDefaultProduct(p);
		sku.setId(1l);
		sku.setName("fgfgdfg");

		OrderItem item = new OrderItem();
		item.setSku(sku);
		item.setId(1l);
		item.setProduct(p);

		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(1l);
		orderEntity.setItems(Arrays.asList(item));
		item.setOrder(orderEntity);

		entityManager.persist(p);
		entityManager.persist(sku);
		entityManager.persist(item);
		entityManager.persist(orderEntity);
		entityManager.getTransaction().commit();
		sku = null;
		p = null;
		item = null;
		orderEntity = null;

		entityManager.clear();
		Session session = (Session)entityManager.unwrap(Session.class);
		session.clear();

		OrderEntity order = entityManager.find(OrderEntity.class, 1l);
		order.getItems().get(0);
		SkuImpl responseItem = session.get(SkuImpl.class, 1l);

		System.out.println();
		entityManager.close();
	}
}
