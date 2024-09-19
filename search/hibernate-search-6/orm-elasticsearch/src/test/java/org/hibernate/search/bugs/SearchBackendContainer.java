package org.hibernate.search.bugs;

import java.io.Closeable;
import java.time.Duration;

import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

class SearchBackendContainer implements Closeable {

	private static final DockerImageName ELASTICSEARCH_IMAGE_NAME =
			DockerImageName.parse( "docker.elastic.co/elasticsearch/elasticsearch" )
					.withTag( "7.16.3" );

	private ElasticsearchContainer elasticsearchContainer;

	public ElasticsearchContainer setUp() {
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
		return elasticsearchContainer;
	}

	@Override
	public void close() {
		try ( ElasticsearchContainer esToClose = this.elasticsearchContainer ) {
			// Nothing to do: we just want resources to get closed.
		}
	}
}
