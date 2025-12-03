package org.hibernate.bugs;

import static org.hibernate.type.SqlTypes.JSON;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Map;
import org.hibernate.annotations.JdbcTypeCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	void hhh19964Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		var entity = new Foo();
		entity.properties = Map.of("a", 1, "b", 2);
		entityManager.persist(entity);

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Entity
	public class Foo {
		@Id
		long id;

		@JdbcTypeCode(JSON)
		Object properties;
	}
}
