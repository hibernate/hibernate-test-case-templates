package org.hibernate.bugs;

import org.hibernate.Session;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <code>PrototypeTestCase</code> - Prototype TestCase
 *
 * @author Vlad Mihalcea
 */
public class PrototypeTestCase extends ORMUnitTestCase {

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] {
                Repository.class
        };
    }

    @Test
    public void hhh_123() {

        doInTransaction(session -> {
            Repository repository = new Repository("Hibernate Test Case Template");
            repository.setId(1L);
            session.save(repository);
        });

        Repository repository = doInTransaction(session -> (Repository) session.get(Repository.class, 1L));
    }

    /**
     * Repository - Repository
     *
     * @author Vlad Mihalcea
     */
    @Entity(name = "Repository")
    public static class Repository {

        @Id
        private Long id;

        private String name;

        public Repository() {
        }

        public Repository(Long id) {
            this.id = id;
        }

        public Repository(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
