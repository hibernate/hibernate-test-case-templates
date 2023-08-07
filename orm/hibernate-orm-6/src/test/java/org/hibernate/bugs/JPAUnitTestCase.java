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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * A test case demonstrating that Hibernate can return duplicate records depending on the
 * row-ordering of the result set.  The entity graph specified on {@link Subscription} does left
 * outer joins on offering, sku_oid, subscription_measurements, and subscription_product_ids.  On
 * a Subscription with an Offering that has multiple productIds, our query will get results like
 * <pre>
 *   subscription_1, org_1, ..., sku_1, ..., 1
 *   subscription_1, org_1, ..., sku_1, ..., 2
 *   subscription_1, org_1, ..., sku_1, ..., 3
 *   subscription_2, org_1, ..., sku_2, ..., 4
 *   subscription_2, org_1, ..., sku_2, ..., 5
 *   subscription_2, org_1, ..., sku_2, ..., 6
 * </pre>
 * <p>
 * From those results, we would expect two Subscription objects each with an Offering that has
 * three productIds if we run "FROM Subscription s WHERE orgId = :orgId".  And this is what
 * Hibernate does.
 * <p>
 * However, it is possible for the database to return the results without grouping them by
 * subscriptionId since no ordering was actually specified in the SQL.
 * <pre>
 *   subscription_1, org_1, ..., sku_1, ..., 1
 *   subscription_2, org_1, ..., sku_2, ..., 4
 *   subscription_1, org_1, ..., sku_1, ..., 2
 *   subscription_2, org_1, ..., sku_2, ..., 5
 *   subscription_1, org_1, ..., sku_1, ..., 3
 *   subscription_2, org_1, ..., sku_2, ..., 6
 * </pre>
 * <p>
 * From these results, we get 6 Subscription objects each with an Offering that has one productId.
 * <p>
 * Whether or not the results are grouped by subscriptionId proved to be very difficult to
 * replicate deterministically.  Instead, I simulated the effect by adding an "ORDER BY random()"
 * clause.
 * </p>
 *
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
      // with large data sets.  On smaller data sets, PostgreSQL returns all the records grouped
      // by subscription_id and the bug does not manifest.
      "FROM Subscription s WHERE orgId = :orgId ORDER BY random()",
  })
  void testHhh17040(String jpql) {
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
        OffsetDateTime.of(2023, 7, 1, 13, 30, 0, 0, ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.SECONDS);
    OffsetDateTime end =
        OffsetDateTime.of(2023, 10, 1, 13, 30, 0, 0, ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.SECONDS);

    List<Offering> offeringList =
        entityManager.createQuery("FROM Offering o", Offering.class).getResultList();

    List<String> subscriptionInserts = new ArrayList<>();
    for (int i = 0; i < subscriptionCount; i++) {
      Offering offering = offeringList.get(i);
      String subscriptionId = "subscription_" + i;
      String orgId = "org_" + i % 50;
      OffsetDateTime startDate = start.minusDays(i % 100);
      OffsetDateTime endDate = end.plusDays(i % 100);
      subscriptionInserts.add(String.format("INSERT INTO subscription ("
          + " end_date,"
          + " sku,"
          + " org_id,"
          + " start_date,"
          + " subscription_id)"
          + " VALUES ('%s','%s','%s','%s','%s');\n",
          endDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
          offering.getSku(),
          orgId,
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
      String offeringInsert = String.format("INSERT INTO offering ("
              + " product_name,"
              + " sku)"
              + " VALUES ('%s','%s');\n", productName, sku);

      StringBuilder productIdInserts = new StringBuilder();
      for (int j = i; j < i + productIdCount; j++) {
        String singleInsert = String.format("INSERT INTO sku_oid ("
            + " sku,"
            + " oid)"
            + " VALUES ('%s', %s);\n", sku, j);
        productIdInserts.append(singleInsert);
      }

      String insertBundle = offeringInsert + productIdInserts;
      offeringInserts.add(insertBundle);
    }
    return offeringInserts;
  }
}
