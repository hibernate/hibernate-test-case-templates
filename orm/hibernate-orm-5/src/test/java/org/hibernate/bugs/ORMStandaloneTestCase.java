package org.hibernate.bugs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
public abstract class ORMStandaloneTestCase {

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
			.buildMetadata();

		sf = metadata.buildSessionFactory();
	}

	// Add your tests, using standard JUnit.

	@Test
	@Ignore
	public void hhh123Test() throws Exception {

	}

	/**
	 * Execute the given logic in a transactional context.
	 * The Session and the Transaction are automatically managed.
	 *
	 * @param executable unit of logic
	 * @param <T> result type
	 * @return result
	 */
	protected <T> T doInTransaction(TransactionExecutable<T> executable) {
		T result = null;
		Session session = null;
		Transaction txn = null;
		try {
			session = sf.openSession();
			executable.beforeTransactionCompletion();
			txn = session.beginTransaction();

			result = executable.execute(session);
			txn.commit();
		} catch (RuntimeException e) {
			if ( txn != null ) txn.rollback();
			throw e;
		} finally {
			executable.afterTransactionCompletion();
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * Execute the given logic in a transactional context.
	 * The Session and the Transaction are automatically managed.
	 *
	 * @param executable unit of logic
	 */
	protected void doInTransaction(TransactionVoidExecutable executable) {
		Session session = null;
		Transaction txn = null;
		try {
			session = sf.openSession();
			executable.beforeTransactionCompletion();
			txn = session.beginTransaction();

			executable.execute(session);
			txn.commit();
		} catch (RuntimeException e) {
			if ( txn != null ) txn.rollback();
			throw e;
		} finally {
			executable.afterTransactionCompletion();
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Executable unit that may return a result.
	 * @param <T> result type
	 */
	protected interface Executable<T> {
		/**
		 * Execute against the given Session
		 * @param session Hibernate session
		 * @return result
		 */
		T execute(Session session);
	}

	/**
	 * Executable unit that doesn't return a result.
	 */
	protected interface VoidExecutable {
		/**
		 * Execute against the given Session
		 * @param session Hibernate session
		 */
		void execute(Session session);
	}

	protected abstract class TransactionExecutable<T> implements Executable<T> {
		/**
		 * Before transaction completion callback
		 */
		void beforeTransactionCompletion() {

		}

		/**
		 * After transaction completion callback
		 */
		void afterTransactionCompletion() {

		}
	}

	protected abstract class TransactionVoidExecutable implements VoidExecutable {
		/**
		 * Before transaction completion callback
		 */
		void beforeTransactionCompletion() {

		}

		/**
		 * After transaction completion callback
		 */
		void afterTransactionCompletion() {

		}
	}
}
