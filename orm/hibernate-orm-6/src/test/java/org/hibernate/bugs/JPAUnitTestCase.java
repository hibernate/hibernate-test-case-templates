package org.hibernate.bugs;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Persistence;
import jakarta.persistence.Table;
import org.hibernate.annotations.PartitionKey;
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
	public void HHH16849() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// Do stuff...

		doInJPA( ()->entityManagerFactory, em -> {
			SalesContact contact = new SalesContact();
			contact.setFirstName("John");
			contact.setAccountId(1L);
			entityManager.persist( contact );

			contact.setFirstName("John Richard");
			entityManager.persist( contact );
		});

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Table(name = "contact_emails")
	@Entity
	public class ContactEmail {
		@Id
		@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "email")
		private String email;


		@ManyToOne(fetch= FetchType.LAZY)
		@JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
		@JoinColumn(name = "contact_id", referencedColumnName = "id", nullable = false)
		private SalesContact contact;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public SalesContact getContact() {
			return contact;
		}

		public void setContact(SalesContact contact) {
			this.contact = contact;
		}
	}

	@Table(name = "contacts")
	@Entity
	public class SalesContact
		implements Serializable {
		@Column(name = "first_name")
		private String firstName;

		@Column(name = "last_name")
		private String lastName;

		@PartitionKey
		@Column(name = "account_id", nullable = false)
		private Long accountId;

		@Id
		@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@OneToMany(mappedBy = "contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
		private Set<ContactEmail> emails;


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

		public Set<ContactEmail> getEmails() {
			return emails;
		}

		public void setEmails(Set<ContactEmail> emails) {
			this.emails = emails;
		}
	}
}
