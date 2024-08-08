package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ToOneJoinFetchTest {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void testToOneAttributeJoinFetch() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        //language=HQL
        var hql = """
                	SELECT a_entity FROM org.hibernate.entities.AEntity a_entity
                	LEFT JOIN FETCH a_entity.b_entity b_entity
                	LEFT JOIN FETCH b_entity.c_entities c_entities
                """;

        var query = entityManager.createQuery(hql, List.class);
        var res = query.getResultList();

        assertEquals(0, res.size());
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
