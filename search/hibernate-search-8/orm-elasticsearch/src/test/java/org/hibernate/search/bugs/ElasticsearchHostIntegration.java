package org.hibernate.search.bugs;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.spi.BootstrapContext;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.hibernate.testing.orm.junit.SettingProvider;

public class ElasticsearchHostIntegration implements Integrator, SettingProvider.Provider<String> {
    private static SearchBackendContainer elasticsearchContainer;

    public ElasticsearchHostIntegration() {
        if (elasticsearchContainer == null) {
            elasticsearchContainer = new SearchBackendContainer();
        }
    }

    @Override
    public void integrate(Metadata metadata, BootstrapContext bootstrapContext, SessionFactoryImplementor sessionFactory) {
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        try (SearchBackendContainer esToClose = this.elasticsearchContainer) {
        }
    }

    @Override
    public String getSetting() {
        return elasticsearchContainer.setUp().getHttpHostAddress();
    }
}
