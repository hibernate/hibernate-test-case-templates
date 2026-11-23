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
		// Do stuff..laimantEntity clmt = new Claim
		
		//Parent Entity persist
		ClaimantEntity clmt = new ClaimantEntity();
		ClaimantFolderPK pk = new ClaimantFolderPK();
		ClaimFolderPK clmPk = new ClaimFolderPK();
		clmPk.setClaimNumber("1111");
		clmPk.setPartitionNumber("11");
		clmPk.setProductionOrTrainingCode("T");
		pk.setClmSubNbr("123");
		pk.setClaimFolderPK(clmPk);
		clmt.setId(pk);
		entityManager.perist(clmt);
		
		//association  of parent entity of same table
		//claimant to Injury OneToOne relation
		InjuryEntity inj = new InjuryEntity ();
		inj.setId(pk);
		inj.setInjuryName("injname");
		clmt.setInjury(inj);
		entityManager.merge(clmt); // issue is here while merging, it's fetchinh claimantEntity joining with injury and party entity..so on if any associations
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
