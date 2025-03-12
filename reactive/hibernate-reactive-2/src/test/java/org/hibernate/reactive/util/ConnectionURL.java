package org.hibernate.reactive.util;

/**
 * JDBC URL connection strings for the property {@link org.hibernate.reactive.provider.Settings#URL} when the database
 * is started the instructions from the
 * <a href="https://github.com/hibernate/hibernate-reactive/blob/main/podman.md#how-to-start-the-test-databases-using-podman">Hibernate Reactive GitHub repository</a>.
 */
public final class ConnectionURL {
	public static final String POSTGRES = "jdbc:postgresql://localhost:5432/hreact?user=hreact&password=hreact";
	public static final String COCKROACH = "jdbc:cockroachdb://localhost:26257/postgres?sslmode=disable&user=root";
	public static final String MYSQL = "jdbc:mysql://localhost/hreact?user=hreact&password=hreact";
	public static final String MARIA = "jdbc:mariadb://localhost:3306/hreact?user=hreact&password=hreact";
	public static final String MSSQL = "jdbc:sqlserver://localhost:1433;Encrypt=false;user=sa;password=~!HReact!~";
	public static final String DB2 = "jdbc:db2://localhost:50000/hreact:user=hreact;password=hreact;";
	public static final String ORACLE = "jdbc:oracle:thin:hreact/hreact@localhost:1521/hreact?user=hreact&password=hreact";
}
