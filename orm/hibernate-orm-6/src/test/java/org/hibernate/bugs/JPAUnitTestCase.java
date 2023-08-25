package org.hibernate.bugs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Id;
import jakarta.persistence.Persistence;
import java.util.List;
import org.hibernate.annotations.Type;
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
	public void hhh17134Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		var entity = entityManager.getMetamodel().managedType(Example.class);

		assertEquals(List.class, entity.getAttribute("list").getJavaType());

		entityManager.close();
	}
	
	@Entity
	static class Example {
		@Id
		private int id;
		
		@Column(columnDefinition = "INTEGER ARRAY")
		@Type(ListArrayType.class)
		private List<Integer> list;
	}
}
