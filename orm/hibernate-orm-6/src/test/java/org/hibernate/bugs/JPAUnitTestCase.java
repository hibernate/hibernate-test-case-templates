package org.hibernate.bugs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy() {
        this.entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    public void hhh18166Test() throws Exception {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        // Do stuff...

        PolicyGroup policyGroup = new PolicyGroup();
        entityManager.persist(policyGroup);

        Policy policy = new Policy(policyGroup);

        policyGroup.addPolicy(policy);

        PolicyGroupRisk policyGroupRisk = new PolicyGroupRisk(policyGroup);
        policyGroup.addGroupRisk(policyGroupRisk);

        PolicyRisk policyRisk = new PolicyRisk(policy, policyGroupRisk);
        policy.addRisk(policyRisk);
        entityManager.persist(policy);

        System.out.println("About to commit");
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
