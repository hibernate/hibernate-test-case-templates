package org.hibernate.search.bugs;

import java.time.Duration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class SearchTestBase {

	private static final DockerImageName ELASTICSEARCH_IMAGE_NAME =
			DockerImageName.parse( "docker.elastic.co/elasticsearch/elasticsearch" )
					.withTag( "8.11.0" );

	private SessionFactory sessionFactory;
	private ElasticsearchContainer elasticsearchContainer;

	@BeforeEach
	public void setUp() {
		elasticsearchContainer = new ElasticsearchContainer( ELASTICSEARCH_IMAGE_NAME )
				.withExposedPorts( 9200, 9300 )
				.waitingFor( new HttpWaitStrategy().forPort( 9200 ).forStatusCode( 200 ) )
				.withStartupTimeout( Duration.ofMinutes( 5 ) )
				.withReuse( true )
				.withEnv( "logger.level", "WARN" )
				.withEnv( "discovery.type", "single-node" )
				.withEnv( "xpack.security.enabled", "false" )
				.withEnv( "ES_JAVA_OPTS", "-Xms1g -Xmx1g" )
				.withEnv( "cluster.routing.allocation.disk.threshold_enabled", "false" );
		elasticsearchContainer.start();

		StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
		registryBuilder.applySetting(
				"hibernate.search.backend.hosts",
				elasticsearchContainer.getHttpHostAddress()
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
		try ( ElasticsearchContainer esToClose = this.elasticsearchContainer;
				SessionFactory sessionFactoryToClose = this.sessionFactory ) {
			// Nothing to do: we just want resources to get closed.
		}
	}

	protected abstract Class<?>[] getAnnotatedClasses();

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
