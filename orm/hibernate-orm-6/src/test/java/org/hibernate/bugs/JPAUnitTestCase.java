package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

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

		Category category = getCategory(1l);
		entityManager.persist(category);

		Product product1 = getProduct(1l);


		createSku(1l, product1);

		createProdCatXref(1l, category, product1);
//########################################################
		Product product2 = getProduct(2l);


		createSku(2l, product2);

		createProdCatXref(2l, category, product2);
//#############################################
		Product product3 = getProduct(3l);


		createSku(3l, product3);

		createProdCatXref(3l, category, product3);
		entityManager.persist(product1);
		entityManager.persist(product2);
		entityManager.persist(product3);

		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.clear();
		entityManager.getTransaction().begin();

		Product product = entityManager.find(ProductImpl.class, 1l);
		product.getDefaultSku().getPriceListSku();
		product.getAllParentCategoryXrefs().get(0);

		Category category1 = entityManager.find(CategoryImpl.class, 1l);

		Product product4 = getProduct(10l);
		createSku(10l, product4);
		createProdCatXref(10l, category1, product4);

		entityManager.persist(product4);
		entityManager.flush();

		entityManager.refresh(product);

		entityManager.refresh(product4);

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private static void createProdCatXref(long id, Category category, Product product1) {
		CategoryProductXref categoryProductXref = new CategoryProductXrefImpl();
		categoryProductXref.setId(id);
		categoryProductXref.setCategory(category);
		categoryProductXref.setProduct(product1);
		categoryProductXref.setDisplayOrder(new BigDecimal(id));

		product1.getAllParentCategoryXrefs().add(categoryProductXref);
		category.getAllProductXrefs().add(categoryProductXref);
	}

	private static void createSku(long id, Product product1) {
		Sku sku1 = new SkuImpl();
		sku1.setId(id);
		sku1.setName("sku"+id);
		sku1.setPriceListSku(new PriceListSkuImpl());
		sku1.setDefaultProduct(product1);

		product1.setDefaultSku(sku1);
	}

	private static Product getProduct(long id) {
		Product product1 = new ProductImpl();
		product1.setId(id);
		product1.setName("product"+id);
		return product1;
	}

	private static Category getCategory(long id) {
		Category category = new CategoryImpl();
		category.setId(id);
		category.setName("cat"+id);
		return category;
	}
}
