package org.hibernate.bugs;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.bugs.entities.ContactTeamUser;
import org.hibernate.bugs.entities.SalesAccount;
import org.hibernate.bugs.entities.SalesContact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class CriteriaTest {

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
	public void restrictedScope() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// Do stuff...

		doInJPA( ()->entityManagerFactory, em -> {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SalesContact> query = builder.createQuery(SalesContact.class);

			Root<SalesContact> root = query.from(SalesContact.class);
			Join<SalesContact, SalesAccount> primaryAccountJoin = root.join("primaryAccount", JoinType.LEFT);


			// Adding restrictions or conditions
			Predicate contactOwnerPredicate = builder.equal(root.get("ownerId"), 1);
			Predicate primaryAccountOwnerPredicate = builder.equal(primaryAccountJoin.get("ownerId"), 1);
			Predicate primaryParentOwnerPredicate = builder.equal(primaryAccountJoin.get("parentOwnerId"), 1);

			Join<SalesContact, ContactTeamUser> teamUserJoin = root.join("teamUsers", JoinType.LEFT);
			Join<SalesContact, ContactTeamUser> primaryAccountTeamUserJoin = primaryAccountJoin.join("teamUsers", JoinType.LEFT);
			Join<SalesContact, ContactTeamUser> parentAccountTeamUsers = primaryAccountJoin.join("parentAccountTeamUsers", JoinType.LEFT);

			Predicate contactTeamUserPredicate = builder.equal(teamUserJoin.get("userId"), 1);
			Predicate accountTeamUserPredicate = builder.equal(primaryAccountTeamUserJoin.get("userId"), 1);
			Predicate parentAccountTeamUserPredicate = builder.equal(parentAccountTeamUsers.get("userId"), 1);

			query.where(contactOwnerPredicate, primaryAccountOwnerPredicate, primaryParentOwnerPredicate,
				contactTeamUserPredicate, accountTeamUserPredicate, parentAccountTeamUserPredicate);

			// Executing the query
			List<SalesContact> users = em.createQuery(query).getResultList();


			entityManager.getTransaction().commit();
			entityManager.close();
		});
	}

	@Test
	public void territoryScope() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// Do stuff...

		doInJPA( ()->entityManagerFactory, em -> {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SalesContact> query = builder.createQuery(SalesContact.class);

			Root<SalesContact> root = query.from(SalesContact.class);
			Join<SalesContact, ContactTeamUser> teamUserJoin = root.join("teamUsers", JoinType.LEFT);
			Join<SalesContact, ContactTeamUser> primaryAccountJoin = root.join("primaryAccount", JoinType.LEFT);
			Join<SalesContact, ContactTeamUser> primaryAccountTeamUserJoin = primaryAccountJoin.join("teamUsers", JoinType.LEFT);
			Join<SalesContact, ContactTeamUser> parentAccountTeamUsers = primaryAccountJoin.join("parentAccountTeamUsers", JoinType.LEFT);


			// Adding restrictions or conditions
			Predicate contactOwnerPredicate = builder.equal(root.get("ownerId"), 1);
			Predicate primaryAccountOwnerPredicate = builder.equal(primaryAccountJoin.get("ownerId"), 1);
			Predicate primaryParentOwnerPredicate = builder.equal(primaryAccountJoin.get("parentOwnerId"), 1);
			Predicate contactTeamUserPredicate = builder.equal(teamUserJoin.get("userId"), 1);
			Predicate accountTeamUserPredicate = builder.equal(primaryAccountTeamUserJoin.get("userId"), 1);
			Predicate parentAccountTeamUserPredicate = builder.equal(parentAccountTeamUsers.get("userId"), 1);

			query.where(contactOwnerPredicate, primaryAccountOwnerPredicate, primaryParentOwnerPredicate,
				contactTeamUserPredicate, accountTeamUserPredicate, parentAccountTeamUserPredicate);

			// Executing the query
			List<SalesContact> users = em.createQuery(query).getResultList();


			entityManager.getTransaction().commit();
			entityManager.close();
		});
	}

}
