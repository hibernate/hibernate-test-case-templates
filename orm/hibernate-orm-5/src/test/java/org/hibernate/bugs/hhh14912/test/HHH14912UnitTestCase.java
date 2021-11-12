package org.hibernate.bugs.hhh14912.test;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.bugs.hhh14912.model.annotation.PKEntityLong;
import org.hibernate.exception.SQLGrammarException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This test relies on inspecting the DDL generation since that is what's wrong. I don't know how to write a test for Hibernate that validates
 * the ddl generation. I included <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/> in the persistence.xml
 * to force the generation and it does get printed in the logs. Hopefully, whoever is going to fix this issue can use this test to validate.
 * 
 * ANNOTATION DDL:
 * 
 * create table PKEntityLong (
 *     pkey bigint not null,
 *      intVal integer not null,
 *      primary key (pkey)
 *  )
 * 
 * XML DDL:
 * 
 * create table XMLPKEntityLong (
 *     pkey bigint not null,
 *      intVal integer not null,
 *      longPK bigint not null,
 *      primary key (pkey)
 *  )
 *  
 *  The issue is that XML schema generation should not be including `longPK` as that is inherited from the non-entity superclass
 *  https://hibernate.atlassian.net/browse/HHH-14912
 */
public class HHH14912UnitTestCase {

    private EntityManagerFactory entityManagerFactory1;
    private EntityManagerFactory entityManagerFactory2;

    @Before
    public void init() {
        entityManagerFactory1 = Persistence.createEntityManagerFactory("simpleAnnPU");
        entityManagerFactory2 = Persistence.createEntityManagerFactory("simpleXmlPU");
    }

    @After
    public void destroy() {
        entityManagerFactory1.close();
        entityManagerFactory2.close();
    }

    @Test
    public void hhh14912Test() throws Exception {
        EntityManager entityManager = entityManagerFactory1.createEntityManager();
        try {
            entityManager.createNativeQuery("select longPK from PKEntityLong").getResultList();
            Assert.fail("Query expected to fail as table field 'PKEntityLong.longPK' should not exist");
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

        entityManager = entityManagerFactory2.createEntityManager();
        try {
            entityManager.createNativeQuery("select longPK from XMLPKEntityLong").getResultList();
            Assert.fail("Query expected to fail as table field 'XMLPKEntityLong.longPK' should not exist");
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
