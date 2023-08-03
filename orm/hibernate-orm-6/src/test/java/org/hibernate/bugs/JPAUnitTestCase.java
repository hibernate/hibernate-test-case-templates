package org.hibernate.bugs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java
 * Persistence API.
 */
class JPAUnitTestCase {
  private EntityManagerFactory entityManagerFactory;

  @BeforeEach
  void init() {
    entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
  }

  @AfterEach
  void destroy() {
    var entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery("DELETE FROM subscription_product_ids;\n").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM subscription_measurements;\n").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM subscription;\n").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM sku_child_sku;\n").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM sku_oid;\n").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM offering;\n").executeUpdate();
    entityManager.getTransaction().commit();
    entityManagerFactory.close();
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "FROM Subscription s WHERE orgId = :orgId",
      "FROM Subscription s WHERE orgId = :orgId ORDER BY s.subscriptionId",
      // The order by random is meant to simulate when the database returns the subscription_ids
      // out of order.  This does not happen frequently, but we do see it happening in Postgresql
      // with large data sets.  On smaller data sets, Postgresql returns all the records grouped
      // by subscription_id and the bug does not manifest.
      "FROM Subscription s WHERE orgId = :orgId ORDER BY random()",
  })
  void testFetch(String jpql) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    int productIdCount = 10;
    int offeringCount = 500;
    int subscriptionCount = 500;

    List<String> offeringInserts = buildOfferingSql(offeringCount, productIdCount);
    for (String s : offeringInserts) {
      entityManager.getTransaction().begin();
      entityManager.createNativeQuery(s).executeUpdate();
      entityManager.getTransaction().commit();
    }

    List<String> subscriptionInserts = buildSubscriptionSql(entityManager, subscriptionCount);
    for (String s : subscriptionInserts) {
      entityManager.getTransaction().begin();
      entityManager.createNativeQuery(s).executeUpdate();
      entityManager.getTransaction().commit();
    }
    entityManager.clear();

    var graph = entityManager.getEntityGraph("graph.SubscriptionSync");

    System.out.println("Begin subscription query: " + jpql);
    Stream<Subscription> subscriptionQuery =
        entityManager.createQuery(jpql, Subscription.class)
            .setParameter("orgId", "org_0")
            .setHint("jakarta.persistence.fetchgraph", graph)
            .getResultStream();
    System.out.println("End subscription query");

    var duplicates =
        subscriptionQuery.filter(x -> x.getSubscriptionId().equals("subscription_100")).collect(Collectors.toList());
    assertEquals(1, duplicates.size());
  }

  private static List<String> buildSubscriptionSql(EntityManager entityManager, int subscriptionCount) {
    OffsetDateTime start =
        OffsetDateTime.of(2023, 7, 1, 13, 30, 00, 00, ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.SECONDS);
    OffsetDateTime end =
        OffsetDateTime.of(2023, 10, 1, 13, 30, 00, 00, ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.SECONDS);

    List<Offering> offeringList =
        entityManager.createQuery("FROM Offering o", Offering.class).getResultList();

    List<String> subscriptionInserts = new ArrayList<>();
    for (int i = 0; i < subscriptionCount; i++) {
      Offering offering = offeringList.get(i);
      String subscriptionNum = "subscription_num_" + i;
      String subscriptionId = "subscription_" + i;
      String accountNumber = "account_" + i % 50;
      String orgId = "org_" + i % 50;
      OffsetDateTime startDate = start.minusDays(i % 100);
      OffsetDateTime endDate = end.plusDays(i % 100);
      subscriptionInserts.add(String.format("insert into subscription ("
          + " account_number,"
          + " billing_account_id,"
          + " billing_provider_id,"
          + " end_date,"
          + " sku,"
          + " org_id,"
          + " quantity,"
          + " subscription_number,"
          + " start_date,"
          + " subscription_id)"
          + " values ('%s','','','%s','%s','%s',10,'%s','%s','%s');\n",
          accountNumber,
          endDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
          offering.getSku(),
          orgId,
          subscriptionNum,
          startDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
          subscriptionId));
    }
    return subscriptionInserts;
  }

  private static List<String> buildOfferingSql(int offeringCount, int productIdCount) {
    List<String> offeringInserts = new ArrayList<>();

    for (int i = 0; i < offeringCount; i++) {
      String sku = "sku_" + i;
      String productName = "product_" + i;
      String offeringInsert = String.format("insert into offering ("
              + " cores,"
              + " derived_sku,"
              + " description,"
              + " has_unlimited_usage,"
              + " hypervisor_cores,"
              + " hypervisor_sockets,"
              + " product_family,"
              + " product_name,"
              + " role,"
              + " sockets,"
              + " sku)"
              + " values (0,'','',false,0,0,'','%s','role',0,'%s');\n", productName, sku);

      StringBuilder productIdInserts = new StringBuilder();
      for (int j = i; j < i + productIdCount; j++) {
        String singleInsert = String.format("insert into sku_oid ("
            + " sku,"
            + " oid)"
            + " values ('%s', %s);\n", sku, j);
        productIdInserts.append(singleInsert);
      }

      String insertBundle = offeringInsert + productIdInserts;
      offeringInserts.add(insertBundle);
    }
    return offeringInserts;
  }
}
