package org.hibernate.search.bugs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.junit.Test;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class YourIT extends SearchTestBase {

  @Override
  public Class<?>[] getAnnotatedClasses() {
    return new Class<?>[]{YourAnnotatedEntity.class, ChildEntity.class};
  }

  @Test
  public void testMassIndexer() throws Exception {
    Session session = getSessionFactory().openSession();
    YourAnnotatedEntity yourEntity1 = new YourAnnotatedEntity(1L, "Jane Smith");
    YourAnnotatedEntity yourEntity2 = new YourAnnotatedEntity(2L, "Jane Doe");
    ChildEntity childEntity = new ChildEntity(1L);
    yourEntity2.childEntity = childEntity;

    Transaction tx = session.beginTransaction();
    session.persist(childEntity);
    session.persist(yourEntity1);
    session.persist(yourEntity2);
    tx.commit();

    MassIndexingJBatchJob.start(session);

    SearchSession searchSession = Search.session(session);
    await().atMost(10, SECONDS).until(searchReturnsTwoEntities(searchSession));
  }

  private Callable<Boolean> searchReturnsTwoEntities(SearchSession searchSession) {
    return () -> searchSession.search(YourAnnotatedEntity.class)
      .where(f -> f.match().field("name").matching("Jane"))
      .fetchHits(10).size() == 2;
  }
}
