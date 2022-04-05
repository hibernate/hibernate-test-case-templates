package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.bugs.entity.Credit;
import org.hibernate.bugs.entity.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

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
    @Test
    public void HHH15159_Test() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // add Person
        Long firstPersonKey = addPerson(entityManager);

        // add Person
        Long secondPersonKey = addPerson(entityManager);

        // direct delete credit, it will success.
        directDeleteCredit(entityManager, firstPersonKey);

        // direct set credit null and persist person, it will fail.
        directSetCreditNull(entityManager, secondPersonKey);

        entityManager.close();
    }

    private Long  addPerson(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        // Do stuff...
        Person person = new Person();
        person.setName("hello world");

        Credit credit = new Credit();
        credit.setReasons(new HashSet<>(Arrays.asList("one", "two")));
        credit.setPerson(person);
        person.setCredit(credit);
        entityManager.persist(person);
        entityManager.getTransaction().commit();
        return person.getId();
    }

    private void directDeleteCredit(EntityManager entityManager, Long key) {

        entityManager.getTransaction().begin();
        Person person = entityManager.find(Person.class, key);

        // I must use entityManager remove credit, it will success
        entityManager.remove(person.getCredit());
        person.getCredit().setPerson(null);
        person.setCredit(null);

        entityManager.persist(person);
        entityManager.getTransaction().commit();
    }

    private void directSetCreditNull(EntityManager entityManager, Long key) {

        entityManager.getTransaction().begin();

        Person person = entityManager.find(Person.class, key);
        person.getCredit().setPerson(null);
        person.setCredit(null);
        entityManager.persist(person);

        entityManager.getTransaction().commit();
    }

}
