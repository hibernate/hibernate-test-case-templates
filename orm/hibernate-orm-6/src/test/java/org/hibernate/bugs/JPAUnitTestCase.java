package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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
    public void hhh123Test() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery(
                        "select " +
                                "c.id as clientId," +
                                "c.name as clientName," +
                                "t.code as typeCode," +
                                "g.id as generationId," +
                                "sum(e.balance)" +
                                "from Card e " +
                                "inner join e.generation g " +
                                "inner join g.type t " +
                                "inner join t.client c " +
                                "group by clientId, typeCode, generationId " +
                                "order by clientName, typeCode, generationId", Object[].class)
                .getResultList();

        Assertions.fail("Strange order by generated (see log)");

        /*
        Strange order by generated

            select
                t1_0.client_id c0,
                c2_0.name c1,
                g1_0.type_code c2,
                c1_0.generation_id c3,
                sum(c1_0.balance) c4
            from
                Card c1_0
            join
                Generation g1_0
                    on g1_0.id=c1_0.generation_id
            join
                CardType t1_0
                    on t1_0.code=g1_0.type_code
            join
                Client c2_0
                    on c2_0.id=t1_0.client_id
            group by
                c0,
                c2,
                c3
            order by
                2,
                3,
                4
         */

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
