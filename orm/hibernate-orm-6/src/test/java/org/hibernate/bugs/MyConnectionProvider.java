package org.hibernate.bugs;

import com.zaxxer.hikari.*;
import io.opentelemetry.api.*;
import io.opentelemetry.instrumentation.jdbc.datasource.*;
import org.hibernate.*;
import org.hibernate.dialect.*;
import org.hibernate.engine.jdbc.connections.internal.*;
import org.hibernate.engine.jdbc.connections.spi.*;
import org.hibernate.hikaricp.internal.*;
import org.hibernate.internal.log.*;
import org.hibernate.internal.util.*;
import org.hibernate.service.*;
import org.hibernate.service.spi.*;

import javax.sql.*;
import java.sql.*;
import java.util.*;

import static org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator.allowJdbcMetadataAccess;

/**
 * Copy paste from {@link HikariCPConnectionProvider} because I couldn't find another
 * way of wrapping a ds without having to parse the url and configuration ^^
 */
public class MyConnectionProvider implements ConnectionProvider, Configurable, Stoppable {

    private static final long serialVersionUID = -9131625057941275711L;
    private boolean isMetadataAccessAllowed = true;

    /**
     * HikariCP configuration.
     */
    private HikariConfig hcfg = null;

    /**
     * HikariCP data source.
     */
    private HikariDataSource hds = null;

    private DataSource ds = null;

    // *************************************************************************
    // Configurable
    // *************************************************************************

    @Override
    public void configure(Map<String, Object> props) throws HibernateException {
        try {
            isMetadataAccessAllowed = allowJdbcMetadataAccess( props );

            ConnectionInfoLogger.INSTANCE.configureConnectionPool( "HikariCP" );

            hcfg = HikariConfigurationUtil.loadConfiguration( props );
            hds = new HikariDataSource( hcfg );

            // this is the DataSource that will not always work as expected by hibernate
            // see https://github.com/open-telemetry/opentelemetry-java-instrumentation/issues/13580
            ds = JdbcTelemetry.builder(OpenTelemetry.noop())
                    .build()
                    .wrap(hds);
        }
        catch (Exception e) {
            ConnectionInfoLogger.INSTANCE.unableToInstantiateConnectionPool( e );
            throw new HibernateException( e );
        }
    }

    // *************************************************************************
    // ConnectionProvider
    // *************************************************************************

    @Override
    public Connection getConnection() throws SQLException {
        return ds != null ? ds.getConnection() : null;
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public DatabaseConnectionInfo getDatabaseConnectionInfo(Dialect dialect) {
        return new DatabaseConnectionInfoImpl(
                hcfg.getJdbcUrl(),
                // Attempt to resolve the driver name from the dialect, in case it wasn't explicitly set and access to
                // the database metadata is allowed
                !StringHelper.isBlank( hcfg.getDriverClassName() ) ? hcfg.getDriverClassName() : extractDriverNameFromMetadata(),
                dialect.getVersion(),
                Boolean.toString( hcfg.isAutoCommit() ),
                hcfg.getTransactionIsolation(),
                hcfg.getMinimumIdle(),
                hcfg.getMaximumPoolSize()
        );
    }

    private String extractDriverNameFromMetadata() {
        if (isMetadataAccessAllowed) {
            try ( Connection conn = getConnection() ) {
                DatabaseMetaData dbmd = conn.getMetaData();
                return dbmd.getDriverName();
            }
            catch (SQLException e) {
                // Do nothing
            }
        }
        return null;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return ConnectionProvider.class.equals( unwrapType )
                || HikariCPConnectionProvider.class.isAssignableFrom( unwrapType )
                || DataSource.class.isAssignableFrom( unwrapType );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> unwrapType) {
        if ( ConnectionProvider.class.equals( unwrapType )
                || HikariCPConnectionProvider.class.isAssignableFrom( unwrapType ) ) {
            return (T) this;
        }
        else if ( HikariDataSource.class.isAssignableFrom( unwrapType ) ) {
            return (T) hds;
        }
        else if ( DataSource.class.isAssignableFrom( unwrapType ) ) {
            return (T) ds;
        }
        else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }

    // *************************************************************************
    // Stoppable
    // *************************************************************************

    @Override
    public void stop() {
        if ( hds != null ) {
            ConnectionInfoLogger.INSTANCE.cleaningUpConnectionPool( "HikariCP" );
            hds.close();
        }
    }
}
