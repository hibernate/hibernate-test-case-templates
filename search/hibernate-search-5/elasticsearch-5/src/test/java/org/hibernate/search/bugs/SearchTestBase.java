package org.hibernate.search.bugs;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class SearchTestBase {

	private SessionFactory sessionFactory;
	private SearchBackendContainer elasticsearchContainer;

	@BeforeEach
	public void setUp() {
		elasticsearchContainer = new SearchBackendContainer();

		StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
		registryBuilder.applySetting(
				"hibernate.search.default.elasticsearch.host",
				elasticsearchContainer.setUp().getHttpHostAddress()
		);
		MetadataSources ms = new MetadataSources( registryBuilder.build() );
		Class<?>[] annotatedClasses = getAnnotatedClasses();
		if ( annotatedClasses != null ) {
			for ( Class<?> entity : annotatedClasses ) {
				ms.addAnnotatedClass( entity );
			}
		}

		Metadata metadata = ms.buildMetadata();

		final SessionFactoryBuilder sfb = metadata.getSessionFactoryBuilder();
		this.sessionFactory = sfb.build();
	}

	@AfterEach
	public void tearDown() {
		try ( SearchBackendContainer esToClose = this.elasticsearchContainer;
				SessionFactory sessionFactoryToClose = this.sessionFactory ) {
			// Nothing to do: we just want resources to get closed.
		}
	}

	protected abstract Class<?>[] getAnnotatedClasses();

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
