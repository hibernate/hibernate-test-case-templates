package org.hibernate.bugs;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Persistence;
import jakarta.persistence.Table;
import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.SQLInsert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class EmbeddedIdTest {

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
			CompositeKey key = new CompositeKey();
			key.setAccountId(1L);

			SalesContact contact = new SalesContact();
			contact.setFirstName("John");
			contact.setKey(key);
			entityManager.persist( contact );

			Assertions.assertNotNull(key.getId());
		});

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Embeddable
	class CompositeKey implements Serializable {
		@Column(name = "account_id")
		@PartitionKey
		private Long accountId;

		@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

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

	@Table(name = "contacts")
	@Entity
	@SQLInsert(
		sql = "insert into contacts (account_id, first_name, last_name) values (?, ?, ?)"
	)
	public class SalesContact
		implements Serializable {

		@EmbeddedId
		private CompositeKey key;

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

		public CompositeKey getKey() {
			return key;
		}

		public void setKey(CompositeKey key) {
			this.key = key;
		}
	}
}
