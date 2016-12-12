package org.hibernate.bugs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
public abstract class ORMStandaloneTestCase {

	private Configuration cfg;

	private SessionFactory sf;

	@Before
	public void setup() {
		cfg = new Configuration();

		// Add your entities here.
		// cfg.addAnnotatedClass(Foo.class);

		// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
		cfg.setProperty("hibernate.show_sql", "true");
		cfg.setProperty("hibernate.format_sql", "true");
		cfg.setProperty("hibernate.hbm2ddl.auto", "update");

		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder();
		ServiceRegistry sr = srb.build();
		sf = cfg.buildSessionFactory(sr);
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
            if ( txn != null && txn.isActive() ) txn.rollback();
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
            if ( txn != null && txn.isActive() ) txn.rollback();
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
