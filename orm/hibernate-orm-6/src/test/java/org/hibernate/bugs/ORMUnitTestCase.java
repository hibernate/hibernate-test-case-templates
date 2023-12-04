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

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Proxy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 * <p>
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
public class ORMUnitTestCase extends BaseCoreFunctionalTestCase {

    // Add your entities here.
    @Override
    protected Class[] getAnnotatedClasses() {
        return new Class[]{
                FooImpl.class,
                BarImpl.class
        };
    }

    @Override
    protected String getBaseForMappings() {
        return "org/hibernate/test/";
    }

    @Override
    protected void configure(Configuration configuration) {
        super.configure(configuration);

        configuration.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());
        configuration.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());
    }

    /**
     * This test fails with {@link org.hibernate.query.QueryArgumentException} in setParameter() method.
     * <pre>
     *     org.hibernate.query.QueryArgumentException: Argument [org.hibernate.bugs.ORMUnitTestCase$FooImpl@f2fec8]
     *     of type [org.hibernate.bugs.ORMUnitTestCase$FooImpl$HibernateProxy$ACmq807n] did not match parameter
     *     type [org.hibernate.bugs.ORMUnitTestCase$FooImpl (n/a)]
     * </pre>
     */
    @Test
    public void proxyAsQueryParameterTest() {
        Session s = openSession();
        Transaction tx = s.beginTransaction();

        BarImpl bar = new BarImpl();
        FooImpl foo = new FooImpl();
        bar.setFoo(foo);
        s.persist(bar);

        Long id = foo.getId();

        session.flush();
        session.clear();

        Foo fooProxy = session.getReference(Foo.class, id);

        Bar loadedBar = session.createQuery("SELECT b FROM Bar b WHERE b.foo = ?1", Bar.class)
                // replacing with Hibernate.unproxy(fooProxy) works, but we don't want to initialize the proxy
                .setParameter(1, fooProxy)
                .getSingleResult();

        assertNotNull(loadedBar);

        tx.commit();
        s.close();
    }

    public interface Bar extends Foo {
    }

    public interface Foo {
    }

    @Entity(name = "Foo")
    @Proxy(proxyClass = Foo.class)
    public static class FooImpl implements Foo {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    @Entity(name = "Bar")
    @Proxy(proxyClass = Bar.class)
    public static class BarImpl implements Bar {

        @ManyToOne(fetch = FetchType.LAZY, targetEntity = FooImpl.class, cascade = CascadeType.ALL)
        @JoinColumn(name = "FOO_ID")
        private Foo foo;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Foo getFoo() {
            return foo;
        }

        public void setFoo(Foo foo) {
            this.foo = foo;
        }
    }
}
