package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
@Testcontainers
class ArrayContainsPostgresTestCase {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.5-alpine");

    private SessionFactory sf;

    @BeforeEach
    void setup() {
        StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
                // Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                .applySetting("hibernate.connection.url", postgres.getJdbcUrl())
                .applySetting("hibernate.connection.username", postgres.getUsername())
                .applySetting("hibernate.connection.password", postgres.getPassword())
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "update");

        Metadata metadata = new MetadataSources(srb.build())
                // Add your entities here.

                .addAnnotatedClass(Post.class)
                .buildMetadata();

        sf = metadata.buildSessionFactory();
    }

    @AfterEach
    void tearDown() {
        if (sf != null) {
            sf.close();
        }
    }

    // Add your tests, using standard JUnit 5:
    @Test
    void should_pass_with_array_contains() throws Exception {
        try (var session = sf.openSession()) {
            var tx = session.beginTransaction();
            var post = new Post();
            post.addTag("present");
            session.persist(post);

            var query = session.createQuery("select p from Post p where array_contains(tags, :tag )", List.class);

            assertThat(query.setParameter("tag", "present").list().size()).isEqualTo(1);
            assertThat(query.setParameter("tag", "absent").list().size()).isEqualTo(0);
            tx.commit();
        }
    }


    @Entity(name = "Post")
    static class Post {
        @Id
        @GeneratedValue
        private Long id;

        @Column(name = "tags", columnDefinition = "text[]")
        private List<String> tags = new ArrayList<>();

        public void addTag(String tag) {
            tags.add(tag);
        }
    }
}
