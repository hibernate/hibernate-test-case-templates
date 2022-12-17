package org.hibernate.bugs;

import entity.Parent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase
{
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy()
    {
        entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    public void hhh15902Test()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createNativeQuery("insert into parents (id) values (1)").executeUpdate();
        entityManager.createNativeQuery("insert into children (id, parent_id, deleted_at) values (1, 1, now())").executeUpdate();

        var record = entityManager.find(Parent.class, 1);
        assertNotNull(record);
        entityManager.getTransaction().commit();
    }
}
