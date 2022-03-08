package org.hibernate.bugs.hhh15113.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.bugs.hhh15113.model.HHH15113Entity;
import org.hibernate.bugs.hhh15113.model.HHH15113Entity_;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HHH15113UnitTestCase {
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hhh15113PU");
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void hhh15113test1() throws Exception {
        System.out.println("Printed Version String: " + org.hibernate.Version.getVersionString());

        // Should pass
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("UPDATE HHH15113Entity t SET t.itemInteger1 = ?1 WHERE t.itemString1 = ?2");
            query.setParameter(1, 9);
            query.setParameter(2, "B");

            query.executeUpdate();
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            if (entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }

        // Equivalent query, but fails
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<HHH15113Entity> cquery = cb.createCriteriaUpdate(HHH15113Entity.class);
            Root<HHH15113Entity> root = cquery.from(HHH15113Entity.class);

            ParameterExpression<Integer> intValue = cb.parameter(Integer.class);
            ParameterExpression<String> strValue = cb.parameter(String.class);

            cquery.set(root.get(HHH15113Entity_.itemInteger1), intValue);
            cquery.where(cb.equal(root.get(HHH15113Entity_.itemString1), strValue));

            Query query = entityManager.createQuery(cquery);
            query.setParameter(intValue, 9);
            query.setParameter(strValue, "B");

            query.executeUpdate();
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            if (entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

    @Test
    public void hhh15113test2() throws Exception {
        System.out.println("Printed Version String: " + org.hibernate.Version.getVersionString());

        // Should pass
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("UPDATE HHH15113Entity t SET t.itemInteger1 = ?1 WHERE t.itemString1 = ?2");
            query.setParameter(1, 9);
            query.setParameter(2, "B");

            query.executeUpdate();
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            if (entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }

        // Equivalent query, but fails
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<HHH15113Entity> cquery = cb.createCriteriaUpdate(HHH15113Entity.class);
            Root<HHH15113Entity> root = cquery.from(HHH15113Entity.class);

            ParameterExpression<Integer> intValue = cb.parameter(Integer.class);
            ParameterExpression<String> strValue = cb.parameter(String.class);

            cquery.set("itemInteger1", intValue);
            cquery.where(cb.equal(root.get("itemString1"), strValue));

            Query query = entityManager.createQuery(cquery);
            query.setParameter(intValue, 9);
            query.setParameter(strValue, "B");

            query.executeUpdate();
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            if (entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }
}
