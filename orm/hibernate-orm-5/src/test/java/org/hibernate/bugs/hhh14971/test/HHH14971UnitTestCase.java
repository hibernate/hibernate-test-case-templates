package org.hibernate.bugs.hhh14971.test;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.exception.SQLGrammarException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HHH14971UnitTestCase {
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("simpleAnnPU");
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void hhh14971Test() throws Exception {
        System.out.println("Printed Version String: " + org.hibernate.Version.getVersionString());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.createNativeQuery("select city from AnnMSCMultiTableEnt").getResultList();
            Assert.fail("Query expected to fail as table.field 'AnnMSCMultiTableEnt.city' should not exist");
        } catch (AssertionError ae) {
            throw ae;
        } catch (Exception e) {
            Throwable root = containsCauseByException(SQLGrammarException.class, e);
            Assert.assertNotNull("Throwable stack did not contain expected " + SQLGrammarException.class, root);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

    private static Throwable containsCauseByException(final Class<?> exceptionClass, Throwable t) {
        if (exceptionClass == null || t == null) {
            return null;
        }

        final ArrayList<Throwable> tList = new ArrayList<Throwable>();
        while (t != null) {
            if (exceptionClass.equals(t.getClass()) || exceptionClass.isAssignableFrom(t.getClass())) {
                return t;
            }

            // Loop detected, not found
            if (tList.contains(t)) {
                return null;
            }

            tList.add(t);
            t = t.getCause();
        }

        // Reached end, not found
        return t;
    }
}
