package org.hibernate.bugs;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.bugs.entities.Cart;
import org.hibernate.bugs.entities.Item;
import org.hibernate.bugs.entities.Wheel;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.testing.orm.junit.JiraKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Reproducer of HHH-18782 
 * <p>
 * “enable_lazy_load_no_trans=true” creates new instance of parent for each lazily loaded child relation
 * 
 * @see <a href="https://hibernate.atlassian.net/browse/HHH-18782">HHH-18782</a>
 */
@JiraKey("HHH-18782")
class HHH18782_JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		Map<String, String> properties = new HashMap<>();
		properties.put(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS, "true");
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" , properties);
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	void hhh18782Test_LazyLoadedChildrenHaveParentInstanceInInverseRelation() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		Cart cart = new Cart();
		cart.setCartNo("1234");
		
		cart.addItem(createItem("1"));
		cart.addItem(createItem("2"));
		cart.addWheel(createWheel("no1"));
		
		entityManager.persist(cart);
		entityManager.flush();
		
		Long offerId = cart.getId();
		entityManager.clear();
		
		Cart cartFromEm = entityManager.find(Cart.class, offerId);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		// lazy loading 

		// Check that we load lazily - working
		assertEquals(2, cartFromEm.getItems().size());
		assertEquals(1, cartFromEm.getWheels().size());
		
		// Fails from here on >>>
		assertSame("item 0 does not have the correct parent instance", cartFromEm, cartFromEm.getItem(0).getCart());
		assertSame("item 1 does not have the correct parent instance", cartFromEm, cartFromEm.getItem(1).getCart());
		assertSame("wheel 0 does not have the correct parent instance", cartFromEm, cartFromEm.getWheel(0).getCart());
	}

	private Item createItem(String itemNo) {
		Item item = new Item();
		item.setItemNo(itemNo);
		return item;
	}

	private Wheel createWheel(String wheelNo) {
		Wheel wheel = new Wheel();
		wheel.setWheelNo(wheelNo);
		return wheel;
	}

}
