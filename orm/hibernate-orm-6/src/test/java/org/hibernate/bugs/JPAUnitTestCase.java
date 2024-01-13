package org.hibernate.bugs;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.envers.Audited;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;

import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

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

		// Do stuff...
		CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
		CriteriaQuery<?> searchCriteriaQuery = builder.createQuery(CustomerEntity.class);
		CriteriaQuery<Long> countCriteriaQuery = builder.createQuery(Long.class);
		Root<CustomerEntity> root = searchCriteriaQuery.from(CustomerEntity.class);
		countCriteriaQuery.from(CustomerEntity.class);
		Node rootNode = new RSQLParser().parse("id!=0");
		Specification specification = (Specification) rootNode.accept(new JpaPredicateVisitor<>());
		Predicate predicate = specification.toPredicate(root, searchCriteriaQuery, builder);
		Predicate countPredicate = specification.toPredicate(root, countCriteriaQuery, builder);
		countCriteriaQuery.select(builder.countDistinct(root));
		searchCriteriaQuery.where(predicate);
		countCriteriaQuery.where(countPredicate);
		entityManager.createQuery(searchCriteriaQuery);
		Query queryCount = entityManager.createQuery(countCriteriaQuery);
		Long count = (Long) queryCount.getSingleResult();

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Audited
	@Entity(name = "CustomerEntity")
	@Table(name = "CUSTOMER_ENTITY")
	public static class CustomerEntity {

		@Id
		@Column(name = "ID", columnDefinition = "INT")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "FIRST_NAME", columnDefinition = "VARCHAR(50)")
		private String firstName;

		@Column(name = "SECOND_NAME", columnDefinition = "VARCHAR(100)")
		private String lastName;

		@Column(name = "CREATED_ON", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = true, updatable = false)
		private Date createdOn;

		@Column(name = "UPDATED_ON", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = true, updatable = true)
		private Date updatedOn;

		// Getters and setters are omitted for brevity
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

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

		public Date getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(Date createdOn) {
			this.createdOn = createdOn;
		}

		public Date getUpdatedOn() {
			return updatedOn;
		}

		public void setUpdatedOn(Date updatedOn) {
			this.updatedOn = updatedOn;
		}
	}
}
