package org.hibernate.bugs;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
public class ORMStandaloneTestCase {

	private SessionFactory sf;

	@Before
	public void setup() {
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
			// Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
			.applySetting( "hibernate.show_sql", "true" )
			.applySetting( "hibernate.format_sql", "true" )
			.applySetting( "hibernate.hbm2ddl.auto", "update" );

		Metadata metadata = new MetadataSources( srb.build() )
		// Add your entities here.
			.addAnnotatedClass( Main.class )
			.buildMetadata();

		sf = metadata.buildSessionFactory();
	}

	// Add your tests, using standard JUnit.

	@Test
	public void hhh123Test() throws Exception {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Main main = new Main();
        main.setStrings(List.of("green", "red"));
        session.persist(main);
        session.flush();
        tx.commit();
        session.clear();

        Transaction newTx = session.beginTransaction();

        // working
        Query<Long> query4 = session.createQuery(
                "select m.id from Main m where array_overlaps(m.strings, array('red'))",
                Long.class);
        List<Long> result4 = query4.getResultList();
        Assertions.assertEquals(1, result4.get(0));

        Query<Long> query5 = session.createQuery(
                "select m.id from Main m where array_overlaps(m.strings, array('red')) = true",
                Long.class);
        List<Long> result5 = query5.getResultList();
        Assertions.assertEquals(1, result5.get(0));

        // not working
        Query<Long> query2 = session.createQuery(
                "select m.id from Main m where array_overlaps(m.strings, array(?1))",
                Long.class);
        query2.setParameter(1, "red");
        List<Long> result2 = query2.getResultList();
        Assertions.assertEquals(1, result2.get(0));

        Query<Long> query3 = session.createQuery(
                "select m.id from Main m where array_overlaps(m.strings, array(?1)) = true",
                Long.class);
        query3.setParameter(1, "red");
        List<Long> result3 = query3.getResultList();
        Assertions.assertEquals(1, result3.get(0));

        Query<Long> query1 = session.createQuery(
                "select m.id from Main m where array_overlaps(m.strings, ?1)",
                Long.class);
        query1.setParameter(1, List.of("red"));
        List<Long> result1 = query1.getResultList();
        Assertions.assertEquals(1, result1.get(0));

        newTx.commit();
        session.close();
	}
}
