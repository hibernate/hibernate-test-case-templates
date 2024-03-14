package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.bugs.enums.ComposeStatus;
import org.hibernate.bugs.enums.Status;
import org.hibernate.bugs.model.ComposeSample;
import org.hibernate.bugs.model.Sample;
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
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    /*
    test executes correctly for mapping the enum with a simple name as in the case of the Sample class and a Status.
    However, if you leave ComposeSample and ComposeStatus defined, the error is released.
    Caused by: org.hibernate.tool.schema.spi.SchemaManagementException:
    Schema-validation: wrong column type encountered in column [compose_status] in table [compose_sample]; found [compose_status (Types#VARCHAR)], but expecting [composestatus (Types#NAMED_ENUM)]
     */
    @Test
    public void shouldValidateCorrectlyWithJustSampleMapping() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Sample(Status.ACTIVE));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
