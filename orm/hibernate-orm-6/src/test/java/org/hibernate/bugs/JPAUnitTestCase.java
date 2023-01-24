package org.hibernate.bugs;

import static org.hibernate.bugs.entities.common.SingleStringValueHolderId.newId;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.bugs.entities.address.AddressA;
import org.hibernate.bugs.entities.address.AddressB;
import org.hibernate.bugs.entities.address.AddressId;
import org.hibernate.bugs.entities.message.Message;
import org.hibernate.bugs.entities.message.MessageId;
import org.hibernate.bugs.entities.user.UserA;
import org.hibernate.bugs.entities.user.UserB;
import org.hibernate.bugs.entities.user.UserId;
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
        System.out.println("destroy");
        entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    public void hhh15969Test() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        // Create Object of Type A
        AddressA addressA1 = new AddressA(newId(AddressId.class));
        UserA userA1 = new UserA(newId(UserId.class));
        addressA1.setUserA(userA1);
        Message messageA1 = new Message(newId(MessageId.class), addressA1);
        entityManager.persist(messageA1);

        // Create Object of Type B
        AddressB addressB1 = new AddressB(newId(AddressId.class));
        UserB userB1 = new UserB(newId(UserId.class));
        addressB1.setUserB(userB1);
        Message messageB1 = new Message(newId(MessageId.class), addressB1);
        entityManager.persist(messageB1);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();

        // We need to create a new Transaction, only then the error occurs.
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.getTransaction().begin();
        Query selectUFromUserU = entityManager2.createQuery("select u from Message u");
        List<Message> resultList = selectUFromUserU.getResultList();
        System.out.println("=======================================");
        resultList.forEach(o -> System.out.println(o.getId().getValue()));
        // with throw org.hibernate.PropertyAccessException: Could not set value of type [org.hibernate.bugs.entities.user.UserA] : `org.hibernate.bugs.entities.address.AddressB.userB` (setter)
        resultList.forEach(o -> System.out.println(o.getAddress().getId().getValue()));
        System.out.println("=======================================");
        entityManager2.getTransaction().commit();
        entityManager2.close();
    }


}
