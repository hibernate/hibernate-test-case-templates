package org.hibernate.bugs;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.annotations.Formula;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Persistence;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reproducer for a gap left open by the fix for https://hibernate.atlassian.net/browse/HHH-20588.
 * <p>
 * HHH-20588 fixed {@code CollectionFetchPaginationQueryTransformer} for the case where the
 * {@code @Formula} property lives on the PRIMARY (root) table of the paginated query. This test
 * demonstrates that the same invalid-SQL-alias problem still occurs when the {@code @Formula}
 * property lives on an entity reached via a chain of two {@code @ManyToOne} joins away from the
 * root ({@code TestNotification -> TestDocument -> TestAccount}), while a sibling
 * {@code @ManyToMany} collection on the root is fetched in the same paginated query.
 * <p>
 * Root cause: the fix only patches the "primary table" branch of the transformer's projector.
 * Selections that come from a joined (non-root) table go through a separate absorption path
 * ({@code AbsorbedColumnCollector}/{@code addAbsorbedSelections}) which still uses the fully
 * resolved formula SQL text as a column name, producing invalid SQL. This is dialect-independent
 * (unlike the already-fixed case, which only surfaced via
 * {@code emulateQueryPartTableReferenceColumnAliasing} on Oracle/DB2/MariaDB/HANA) — we saw the
 * production symptom as a plain {@code PSQLException} on PostgreSQL.
 * <p>
 * Run with: mvn test -Dtest="FormulaJoinChainCollectionFetchPaginationTest" -Dversion.org.hibernate.orm=&lt;X&gt;
 */
class FormulaJoinChainCollectionFetchPaginationTest {

	private EntityManagerFactory entityManagerFactory;

	private static final List<String> CAPTURED_SQL = new CopyOnWriteArrayList<>();

	@BeforeEach
	void init() {
		CAPTURED_SQL.clear();
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	/**
	 * Paginated query that fetches a @ManyToMany collection on the root entity (this is what
	 * makes Hibernate engage CollectionFetchPaginationQueryTransformer for true DB-side
	 * pagination) while also join-fetching down to a @Formula property two @ManyToOne hops
	 * away from the root. The formula's raw SQL text should never leak as a "column name" in
	 * the generated derived-table SQL.
	 */
	@Test
	void testPaginatedQueryWithCollectionFetchAndTwoJoinsDeepFormula() {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();

		for ( long i = 1; i <= 10; i++ ) {
			TestAccount account = new TestAccount();
			account.setId( i );
			account.setAccountNumber( "ACC-" + i );
			account.setName( "Account " + i );
			em.persist( account );

			TestDocument document = new TestDocument();
			document.setId( i );
			document.setAccount( account );
			em.persist( document );

			TestNotification notification = new TestNotification();
			notification.setId( i );
			notification.setDocument( document );
			em.persist( notification );

			TestUser user = new TestUser();
			user.setId( i );
			user.setUsername( "user" + i );
			em.persist( user );

			notification.getNotifiedUsers().add( user );
		}

		em.getTransaction().commit();
		em.close();

		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();

		CAPTURED_SQL.clear();

		TypedQuery<TestNotification> query = em.createQuery(
				"select n from TestNotification n "
						+ "left join fetch n.notifiedUsers u "
						+ "join fetch n.document d "
						+ "join fetch d.account a "
						+ "order by n.id",
				TestNotification.class );
		query.setFirstResult( 0 );
		query.setMaxResults( 5 );

		// This should succeed but currently produces invalid SQL: the @Formula on TestAccount
		// (two @ManyToOne joins away from the paginated root TestNotification) is absorbed into
		// the pagination derived table using its raw, unaliased SQL text instead of a safe
		// column name/alias.
		List<TestNotification> results = query.getResultList();

		em.getTransaction().commit();
		em.close();

		assertThat( results ).hasSize( 5 );
		assertThat( results.get( 0 ).getDocument().getAccount().getAccountLabel() ).isNotBlank();

		boolean transformerApplied = CAPTURED_SQL.stream()
				.anyMatch( sql -> sql.toLowerCase().contains( "from (select" ) );
		assertThat( transformerApplied )
				.as( "CollectionFetchPaginationQueryTransformer should have rewritten the query for true DB-side pagination" )
				.isTrue();

		boolean rawFormulaLeaked = CAPTURED_SQL.stream()
				.anyMatch( sql -> sql.contains( "||" ) );
		assertThat( rawFormulaLeaked )
				.as( "the raw @Formula SQL text must not leak as a column name/alias in the generated SQL" )
				.isFalse();
	}

	/**
	 * StatementInspector that captures all SQL statements for assertion.
	 * Registered in persistence.xml via hibernate.session_factory.statement_inspector.
	 */
	public static class SqlCapture implements StatementInspector {

		@Override
		public String inspect(String sql) {
			CAPTURED_SQL.add( sql );
			return sql;
		}
	}

	// ---- Entities ----
	// Names/fields are a generic, anonymized stand-in for the production model that surfaced
	// this bug (a notification entity with a @ManyToMany recipient collection, referencing a
	// document, which in turn references an account carrying a @Formula display label).

	@Entity(name = "TestNotification")
	@Table(name = "TEST_NOTIFICATIONS")
	public static class TestNotification {

		@Id
		@Column(name = "NOTIFICATION_ID")
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "DOCUMENT_ID", nullable = false)
		private TestDocument document;

		@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(name = "TEST_NOTIFICATION_USER",
				joinColumns = @JoinColumn(name = "NOTIFICATION_ID"),
				inverseJoinColumns = @JoinColumn(name = "USER_ID"))
		private Set<TestUser> notifiedUsers = new LinkedHashSet<>();

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public TestDocument getDocument() {
			return document;
		}

		public void setDocument(TestDocument document) {
			this.document = document;
		}

		public Set<TestUser> getNotifiedUsers() {
			return notifiedUsers;
		}
	}

	@Entity(name = "TestDocument")
	@Table(name = "TEST_DOCUMENTS")
	public static class TestDocument {

		@Id
		@Column(name = "DOCUMENT_ID")
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "ACCOUNT_ID", nullable = false, updatable = false)
		private TestAccount account;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public TestAccount getAccount() {
			return account;
		}

		public void setAccount(TestAccount account) {
			this.account = account;
		}
	}

	@Entity(name = "TestAccount")
	@Table(name = "TEST_ACCOUNTS")
	public static class TestAccount {

		@Id
		@Column(name = "ACCOUNT_ID")
		private Long id;

		@Column(name = "ACCOUNT_NUMBER")
		private String accountNumber;

		@Column(name = "NAME")
		private String name;

		@Formula("ACCOUNT_NUMBER || ' ' || NAME")
		private String accountLabel;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAccountLabel() {
			return accountLabel;
		}
	}

	@Entity(name = "TestUser")
	@Table(name = "TEST_USERS")
	public static class TestUser {

		@Id
		@Column(name = "USER_ID")
		private Long id;

		@Column(name = "USERNAME")
		private String username;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}
}
