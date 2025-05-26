package org.hibernate.reactive.util;

import java.util.function.Supplier;
import org.testcontainers.containers.CockroachContainer;
import org.testcontainers.containers.Db2Container;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.hibernate.reactive.util.DockerImage.imageName;

/**
 * The only purpose of this class is to make it easier to switch among the available databases
 * for the unit tests.
 * <p>
 * It's a wrapper around the testcontainers classes.
 */
public enum Database {
	POSTGRESQL( () -> new PostgreSQLContainer<>( imageName( "postgres", "16.3" ) ) ),
	MYSQL( () -> new MySQLContainer<>( imageName( "mysql", "8.4.0" ) ) ),
	MARIADB( () -> new MariaDBContainer<>( imageName( "mariadb", "11.4.2" ) ) ),
	DB2( () -> new Db2Container( imageName( "icr.io", "db2_community/db2", "12.1.0.0" ) ).acceptLicense() ),
	COCKROACHDB( () -> new CockroachContainer( imageName( "cockroachdb/cockroach", "v24.1.0" ) ) ),
	ORACLE( Database::newOracleContainer ),
	MSSQL( Database::newMSSqlServer );

	private final Supplier<JdbcDatabaseContainer<?>> containerSupplier;

	Database(Supplier<JdbcDatabaseContainer<?>> supplier) {
		this.containerSupplier = supplier;
	}

	/**
	 * Start the container using testcontainers and print the log on System.out.
	 */
	public JdbcDatabaseContainer<?> startContainer() {
		return startContainer( true );
	}

	/**
	 * Start the database using testcontainers.
	 *
	 * @param enableLog if true, print the container log
	 */
	public JdbcDatabaseContainer<?> startContainer(boolean enableLog) {
		JdbcDatabaseContainer<?> jdbcDatabaseContainer = containerSupplier.get();
		jdbcDatabaseContainer
				.withLogConsumer( of -> logContainerOutput( of.getUtf8String() ) )
				.withReuse( true )
				.start();
		return jdbcDatabaseContainer;
	}

	private static MSSQLServerContainer<?> newMSSqlServer() {
		return new MSSQLServerContainer<>( imageName( "mcr.microsoft.com", "mssql/server", "2022-latest" ) )
				.acceptLicense();
	}

	private static OracleContainer newOracleContainer() {
		return new OracleContainer( imageName( "gvenzl/oracle-free", "23-slim-faststart" )
											.asCompatibleSubstituteFor( "gvenzl/oracle-xe" ) )
				.withStartupAttempts( 1 )

				// We need to limit the maximum amount of CPUs being used by the container;
				// otherwise the hardcoded memory configuration of the DB might not be enough to successfully boot it.
				// See https://github.com/gvenzl/oci-oracle-xe/issues/64
				// I choose to limit it to "2 cpus": should be more than enough for any local testing needs,
				// and keeps things simple.
				.withCreateContainerCmdModifier( cmd -> cmd.getHostConfig().withCpuCount( 2L ) );
	}

	private static void logContainerOutput(String line) {
		System.out.print( line );
	}
}
