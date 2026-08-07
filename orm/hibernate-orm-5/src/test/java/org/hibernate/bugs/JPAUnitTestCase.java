package org.hibernate.bugs;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.bugs.model.ContentDL;
import org.hibernate.bugs.model.ProductDL;
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
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		ProductDL productDL=new ProductDL()
				.setName("Product 1");
		entityManager.persist(productDL);
		
		ContentDL content1DL=new ContentDL()
				.setName("Content 1");
		content1DL.setProduct(productDL);
		entityManager.persist(content1DL);
		
		ContentDL content2DL=new ContentDL()
				.setName("Content 2");
		content2DL.setProduct(productDL);
		entityManager.persist(content2DL);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		//Load product and store it in session cache
		//Removing this line code works well due to "Product 1" dont be cached in session cache!
		entityManager.find(ProductDL.class,productDL.getId());
		
		//Relaod same product but now providing an EntityGraph with contents
		EntityGraph<ProductDL> eg=entityManager.createEntityGraph(ProductDL.class);
		eg.addSubgraph("contents");
		Map<String,Object> props=new HashMap<String,Object>();
		props.put(
				"javax.persistence.loadgraph",
				eg);
		productDL=entityManager.find(ProductDL.class,productDL.getId(),props);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		productDL.getContents();
	}
}
