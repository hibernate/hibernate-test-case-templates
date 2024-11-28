package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
		
		/*-HitCount event = new HitCount();

		entityManager.persist(event);

		Catalogue catalogue = new Catalogue();
		entityManager.persist(catalogue);

		Map<String, Object> args = new LinkedHashMap<String, Object>();

		args.put("newPartnumber", "23456");
		args.put("partnumber", "12345");

		String query = "update HitCount set partNumber=:newPartnumber where partNumber=:partnumber";
		// String query = "update HitCount hc set hc.partNumber=:newPartnumber
		// where hc.partNumber=:partnumber";
		// String query = "update HitCount hc set hc.partNumber=:newPartnumber
		// where fk(o.catalogue)=:partnumber";

		Query dbQuery = entityManager.createQuery(query);

		for (Map.Entry<String, Object> entry : args.entrySet()) {
			try {

				dbQuery.setParameter(entry.getKey(), entry.getValue());
			} catch (IllegalArgumentException e) {
				// ignored
			}

		}

		dbQuery.executeUpdate();*/
	
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
