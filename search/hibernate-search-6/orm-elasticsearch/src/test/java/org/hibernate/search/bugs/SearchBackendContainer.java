package org.hibernate.search.bugs;

import java.io.Closeable;
import java.time.Duration;

import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

class SearchBackendContainer implements Closeable {

	private static final DockerImageName ELASTICSEARCH_IMAGE_NAME =
			DockerImageName.parse( "docker.io/elastic/elasticsearch:7.16.3" )
					.asCompatibleSubstituteFor( "docker.elastic.co/elasticsearch/elasticsearch" );

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
				// The bundled JDK 17.0.1 can crash reading cgroup v2 metrics in the JVM options parser.
				// JDK_JAVA_OPTIONS reaches the parser before ES_JAVA_OPTS is applied.
				.withEnv( "JDK_JAVA_OPTIONS", "-XX:-UseContainerSupport" )
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
