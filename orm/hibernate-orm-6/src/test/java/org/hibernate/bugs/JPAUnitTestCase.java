package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Map;
import org.hibernate.annotations.TenantId;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
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

	@Entity
	@Table(name = "display_id")
	public class DisplayIdBE {

		@EmbeddedId
		private DisplayIdKeyBE id;

		@Column(name = "display_id_value", nullable = false)
		private long displayIdValue;
	}

	public enum DisplayIdType {
		TYPE1,
		TYPE2
	}

	@Embeddable
	public class DisplayIdKeyBE implements Serializable {

		@TenantId
		@Column(name = "tenant_id", nullable = false)
		private Long tenantId;

		@Column(name = "type", nullable = false)
		@Enumerated(EnumType.STRING)
		private DisplayIdType type;

		// For Hibernate
		protected DisplayIdKeyBE() {}
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
