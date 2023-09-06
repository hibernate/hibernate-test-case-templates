package org.hibernate.bugs;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
		//	.addAnnotatedClass( Foo.class )
                .addAnnotatedClass(Main.class)
                .addAnnotatedClass(Parent.class)
                .addAnnotatedClass(Child.class)
			.buildMetadata();

		sf = metadata.buildSessionFactory();
	}

	// Add your tests, using standard JUnit.

	@Test
	public void hhh17178Test() throws Exception {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Main main = new Main();
        session.persist(main);
        session.flush();
        tx.commit();
        session.clear();
        Transaction newTx = session.beginTransaction();
        List<Main> result = session.createQuery(
                "select m from Main m left join treat(m.parents as Child) c where c is null",
                Main.class).getResultList();
        Assertions.assertEquals(1, result.size());
        newTx.commit();
        session.close();
	}
}
