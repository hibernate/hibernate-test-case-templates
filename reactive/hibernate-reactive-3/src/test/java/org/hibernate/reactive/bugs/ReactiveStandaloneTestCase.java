package org.hibernate.reactive.bugs;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.reactive.bugs.model.MyEntity;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.reactive.util.Database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.smallrye.mutiny.Uni;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.util.concurrent.CompletableFuture;
import org.testcontainers.containers.JdbcDatabaseContainer;

/**
 * Demonstrates how to develop a standalone test case for Hibernate Reactive using
 * the {@link org.hibernate.reactive.mutiny.Mutiny.SessionFactory}
 * or {@link org.hibernate.reactive.stage.Stage.SessionFactory}.
 * <p>
 * It uses the {@link VertxExtension} to ease the testing of async code using JUnit.
 * </p>
 * <p>
 * There are two test methods (feel free to pick your favorite):
 * <ui>
 * <li>{@link #testWithMutiny(VertxTestContext)} for using the Mutiny API</li>
 * <li>{@link #testWithStage(VertxTestContext)} for using the Stage API</li>
 * </ui>
 * </p>
 * <p>
 * Databases for the tests start via <a href="https://java.testcontainers.org/">Testcontainers</a>.
 * The configuration of the containers is in {@link Database} and the test
 * can run on different databases by changing the value of {@link #SELECTED_DB}.
 * </p>
 */
@ExtendWith(VertxExtension.class)
public class ReactiveStandaloneTestCase {

	/**
	 * The selected database to run the test against.
	 * If the value is set to null, no container will start.
	 *
	 * @see Database
 	 */
	private static final Database SELECTED_DB = Database.POSTGRESQL;

	private static JdbcDatabaseContainer<?> container;
	private static SessionFactory sf;

	@BeforeAll
	public static void startContainer() {
		if ( SELECTED_DB != null ) {
			container = SELECTED_DB.startContainer();
		}
	}

	@BeforeAll
	public static void setup() {
		StandardServiceRegistryBuilder srb = new ReactiveServiceRegistryBuilder()
				.applySetting( "hibernate.connection.url", container.getJdbcUrl() )
				.applySetting( "hibernate.connection.username", container.getUsername() )
				.applySetting( "hibernate.connection.password", container.getPassword() )
				.applySetting( "hibernate.hbm2ddl.auto", "update" )
				.applySetting( "hibernate.show_sql", "true" )
				.applySetting( "hibernate.highlight_sql", "true" )
				.applySetting( "hibernate.format_sql", "true" );

		Metadata metadata = new MetadataSources( srb.build() )
				// Add your entities here.
				.addAnnotatedClass( MyEntity.class )
				.buildMetadata();

		sf = metadata.buildSessionFactory();
	}

	@Test
	public void testWithMutiny(VertxTestContext context) {
		Mutiny.SessionFactory mutinySf = sf.unwrap( Mutiny.SessionFactory.class );
		mutinySf
				// Example of transactional block
				.withTransaction( session -> {
					return Uni.createFrom().voidItem();
				} )
				// Subscribe the uni and wait until it completes
				.subscribe().with( res -> context.completeNow(), context::failNow );
	}

	@Test
	public void testWithStage(VertxTestContext context) {
		Stage.SessionFactory stageSf = sf.unwrap( Stage.SessionFactory.class );
		stageSf
				// Example of transactional block
				.withTransaction( session -> {
					return CompletableFuture.completedFuture( null );
				} )
				// Stop the test when the CompletionStage completes
				.whenComplete( (res, err) -> {
					if ( err != null ) {
						context.failNow( err );
					}
					else {
						context.completeNow();
					}
				} );
	}

	// It's important to always close the factory at the end of the tests.
	@AfterAll
	public static void closeSessionFactory() {
		if ( sf != null ) {
			sf.close();
		}
	}

	@AfterAll
	public static void stopContainer() {
		if ( container != null ) {
			container.stop();
		}
	}
}
