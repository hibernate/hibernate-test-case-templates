/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 *
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
public abstract class ORMUnitTestCase extends BaseCoreFunctionalTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
//				Foo.class,
//				Bar.class
		};
	}

	// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
	@Override
	protected String[] getMappings() {
		return new String[] {
//				"Foo.hbm.xml",
//				"Bar.hbm.xml"
		};
	}
	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

//		configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	// Add your tests, using standard JUnit.
	@Test
	@Ignore
	public void hhh123Test() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		// Do stuff...
		tx.commit();
		s.close();
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
            session = sessionFactory().openSession();
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
            session = sessionFactory().openSession();
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
    @FunctionalInterface
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
    @FunctionalInterface
    protected interface VoidExecutable {
        /**
         * Execute against the given Session
         * @param session Hibernate session
         */
        void execute(Session session);
    }

    @FunctionalInterface
    protected interface TransactionExecutable<T> extends Executable<T> {
        /**
         * Before transaction completion callback
         */
        default void beforeTransactionCompletion() {

        }

        /**
         * After transaction completion callback
         */
        default void afterTransactionCompletion() {

        }
    }

    protected interface TransactionVoidExecutable extends VoidExecutable {
        /**
         * Before transaction completion callback
         */
        default void beforeTransactionCompletion() {

        }

        /**
         * After transaction completion callback
         */
        default void afterTransactionCompletion() {

        }
    }
}
