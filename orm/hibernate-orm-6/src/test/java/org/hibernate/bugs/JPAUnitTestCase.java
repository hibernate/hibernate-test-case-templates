package org.hibernate.bugs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import org.hibernate.bugs.entity.MainEntity;
import org.hibernate.bugs.entity.RelatedEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @AfterEach
    void destroy() {
        entityManagerFactory.close();
    }

    @Test
    void hhh19211Test() throws Exception {

        long id = fillData();

        var threadsCount = 50;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadsCount);

        Thread[] threads = new Thread[threadsCount];
        for (var i = 0; i < threadsCount; i++) {
            var thread = new Thread(() -> {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                var transaction = entityManager.getTransaction();

                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }

                transaction.begin();

                TypedQuery<MainEntity> query = entityManager.createQuery(
                    "SELECT m FROM MainEntity m INNER JOIN m.relatedEntity WHERE m.id = :id",
                    MainEntity.class
                );
                query.setParameter("id", id);
                // test passes, if followOnLocking is disabled
                query.setHint("hibernate.query.followOnLocking", true);
                query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
                var result = query.getSingleResult();

                result.mainCounter++;
                result.relatedEntity.get(0).relatedCounter++;
                entityManager.merge(result);
                entityManager.flush();

                transaction.commit();
                entityManager.close();
            });

            thread.start();
            threads[i] = thread;
        }

        for (var thread : threads) {
            thread.join();
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();
        var entity = entityManager.find(MainEntity.class, id);
        assertEquals(threadsCount, entity.mainCounter);
        assertEquals(threadsCount, entity.relatedEntity.get(0).relatedCounter);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private long fillData() {
        var entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();
        MainEntity mainEntity = new MainEntity();
        RelatedEntity relatedEntity = new RelatedEntity();
        relatedEntity.relatedCounter = 0;
        relatedEntity.main = mainEntity;
        mainEntity.mainCounter = 0;
        mainEntity.relatedEntity = List.of(relatedEntity);

        entityManager.persist(relatedEntity);
        entityManager.persist(mainEntity);

        transaction.commit();
        entityManager.close();
        return mainEntity.id;
    }

}
