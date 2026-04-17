package org.hibernate.search.bugs;

import org.hibernate.Transaction;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.testing.bytecode.enhancement.extension.BytecodeEnhanced;
import org.hibernate.testing.orm.junit.BootstrapServiceRegistry;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.SettingProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use this template <b>only</b> if you need Hibernate ORM bytecode enhancement to reproduce your issue.
 */
@DomainModel(
        annotatedClasses = {
                YourAnnotatedEntity.class
                // Add your entities here, e.g.:
                // Foo.class,
                // Bar.class
        }
)
@ServiceRegistry(
        settingProviders = @SettingProvider(settingName = "hibernate.search.backend.hosts", provider = ElasticsearchHostIntegration.class)
)
@BootstrapServiceRegistry(
        integrators = ElasticsearchHostIntegration.class
)
@SessionFactory
@BytecodeEnhanced(testEnhancedClasses = {YourAnnotatedEntity.class})
class YourBytecodeEnhancedIT {

    @Test
    void testYourBug(SessionFactoryScope scope) {
        scope.inSession(s -> {
            YourAnnotatedEntity yourEntity1 = new YourAnnotatedEntity(1L, "Jane Smith");
            YourAnnotatedEntity yourEntity2 = new YourAnnotatedEntity(2L, "John Doe");

            Transaction tx = s.beginTransaction();
            s.persist(yourEntity1);
            s.persist(yourEntity2);
            tx.commit();
        });

        scope.inSession(s -> {
            SearchSession searchSession = Search.session(s);

            List<YourAnnotatedEntity> hits = searchSession.search(YourAnnotatedEntity.class)
                    .where(f -> f.match().field("name").matching("smith"))
                    .fetchHits(20);

            assertThat(hits)
                    .hasSize(1)
                    .element(0).extracting(YourAnnotatedEntity::getId)
                    .isEqualTo(1L);
        });
    }

}
