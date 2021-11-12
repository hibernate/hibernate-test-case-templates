package org.hibernate.bugs.hhh14910.test;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
 * create table AnnEmbedMultiTableEnt (
 *     id integer not null,
 *     name varchar(255),
 *      primary key (id)
 *  )
 *
 * create table SEC_TABLEEMB (
 *     city varchar(255),
 *     state varchar(255),
 *     street varchar(255),
 *     zip varchar(255),
 *     id integer not null,
 *      primary key (id)
 *  )
 * 
 * XML DDL:
 * 
 * create table XMLEmbedMultiTableEnt (
 *     id integer not null,
 *     city varchar(255),
 *     name varchar(255),
 *     state varchar(255),
 *     street varchar(255),
 *     zip varchar(255),
 *      primary key (id)
 *  )
 *
 * create table XSEC_TABLEEMB (
 *     city varchar(255),
 *     state varchar(255),
 *     street varchar(255),
 *     zip varchar(255),
 *     id integer not null,
 *      primary key (id)
 *
 *)
 *
 *  The issue is that XML schema generation should not be including the Embeddable mappings in the XMLEmbedMultiTableEnt table as they should be overridden to the Secondary Table.
 *  https://hibernate.atlassian.net/browse/HHH-14910
 */
public class HHH14910UnitTestCase {
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
    public void hhh14910Test() throws Exception {
        // Verify that the primary table does not contain the fields that should be on the secondary table
        EntityManager entityManager = entityManagerFactory1.createEntityManager();
        try {
            entityManager.createNativeQuery("select state from AnnEmbedMultiTableEnt").getResultList();
            Assert.fail("Query expected to fail as table field 'AnnEmbedMultiTableEnt.state' should not exist");
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

        // Verify that the secondary table contains the fields it is configured to have
        entityManager = entityManagerFactory1.createEntityManager();
        try {
            entityManager.createNativeQuery("select city from SEC_TABLEEMB").getResultList();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }

        // Verify that the primary table does not contain the fields that should be on the secondary table
        entityManager = entityManagerFactory2.createEntityManager();
        try {
            entityManager.createNativeQuery("select state from XMLEmbedMultiTableEnt").getResultList();
            Assert.fail("Query expected to fail as table field 'XMLEmbedMultiTableEnt.state' should not exist");
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

        // Verify that the secondary table contains the fields it is configured to have
        entityManager = entityManagerFactory2.createEntityManager();
        try {
            entityManager.createNativeQuery("select city from XSEC_TABLEEMB").getResultList();
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