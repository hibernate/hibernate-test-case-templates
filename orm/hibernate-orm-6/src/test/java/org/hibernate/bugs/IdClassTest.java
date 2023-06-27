package org.hibernate.bugs;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Persistence;
import jakarta.persistence.Table;
import org.hibernate.annotations.PartitionKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class IdClassTest {

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
	public void HHH16849() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// Do stuff...

		doInJPA( ()->entityManagerFactory, em -> {

			SalesContact contact = new SalesContact();
			contact.setFirstName("John");
			contact.setAccountId(1L);
			entityManager.persist( contact );

			Assertions.assertNotNull(contact.getId());
		});

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Table(name = "contacts")
	@Entity
	@IdClass(CompositeKey.class)
	public class SalesContact
		implements Serializable {

		@Id
		@Column(name = "account_id")
		@PartitionKey
		private Long accountId;

		@Id
		@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "first_name")
		private String firstName;

		@Column(name = "last_name")
		private String lastName;


		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public Long getAccountId() {
			return accountId;
		}

		public void setAccountId(Long accountId) {
			this.accountId = accountId;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}

	static class CompositeKey implements Serializable {
		private Long accountId;
		private Long id;


		public CompositeKey() {
		}

		public CompositeKey(Long accountId, Long id) {
			this.accountId = accountId;
			this.id = id;
		}

		public Long getAccountId() {
			return accountId;
		}

		public void setAccountId(Long accountId) {
			this.accountId = accountId;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}
}
