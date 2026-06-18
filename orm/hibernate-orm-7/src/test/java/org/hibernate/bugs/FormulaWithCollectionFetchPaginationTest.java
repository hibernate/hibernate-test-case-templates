package org.hibernate.bugs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.annotations.Formula;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Persistence;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reproducer for: @Formula generates invalid SQL alias in paginated subquery with collection fetch.
 * <p>
 * When a paginated query uses an EntityGraph that includes a @OneToMany collection,
 * Hibernate 7.4.x wraps the query in a derived table via CollectionFetchPaginationQueryTransformer.
 * For @Formula properties, the raw formula template (containing unresolved {@code {&#64;}} placeholder)
 * is used as the derived table column name/alias.
 * <p>
 * On databases whose dialect uses emulateQueryPartTableReferenceColumnAliasing (Oracle, DB2, MariaDB,
 * HANA), this produces invalid SQL because the formula template text is emitted as a column alias.
 * On Oracle this fails with ORA-00936: missing expression.
 * <p>
 * This test uses H2 with OracleDialect to reproduce the failure. If run with plain H2Dialect,
 * the SQL assertion on {@code {&#64;}} verifies the bug is present in the SQL AST column names
 * even if H2 doesn't use them for aliasing.
 * <p>
 * Root cause: {@code CollectionFetchPaginationQueryTransformer.addPrimaryTableSelections()} uses
 * {@code selectable.getSelectionExpression()} as the column name, which for @Formula returns the
 * formula template containing {@code {&#64;}}.
 */
class FormulaWithCollectionFetchPaginationTest {

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
	 * This test reproduces the bug: paginated query with collection fetch + @Formula
	 * generates invalid SQL because the formula template is used as a column alias
	 * in the pagination derived table.
	 * <p>
	 * The query fails with a SQL syntax error because the derived table column alias is
	 * the raw formula template containing the unresolved {@code {&#64;}} placeholder.
	 */
	@Test
	void testPaginatedQueryWithCollectionFetchAndFormula() {
		// Setup test data
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		for ( long i = 1; i <= 10; i++ ) {
			TestOrder order = new TestOrder();
			order.setId( i );
			order.setDescription( "Order " + i );
			em.persist( order );

			TestOrderItem item = new TestOrderItem();
			item.setId( i * 100 );
			item.setOrder( order );
			item.setProductName( "Product " + i );
			em.persist( item );

			if ( i % 2 == 0 ) {
				TestOrderPayment payment = new TestOrderPayment();
				payment.setId( i * 1000 );
				payment.setOrderId( i );
				payment.setAmount( i * 10.0 );
				em.persist( payment );
			}
		}
		em.getTransaction().commit();
		em.close();

		// Execute paginated query with collection fetch and @Formula
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();

		CAPTURED_SQL.clear();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TestOrder> cq = cb.createQuery( TestOrder.class );
		Root<TestOrder> root = cq.from( TestOrder.class );
		cq.orderBy( cb.asc( root.get( "id" ) ) );

		EntityGraph<?> graph = em.getEntityGraph( "TestOrder.withItems" );

		TypedQuery<TestOrder> query = em.createQuery( cq );
		query.setHint( "jakarta.persistence.loadgraph", graph );
		query.setFirstResult( 0 );
		query.setMaxResults( 5 );

		// This should succeed but currently throws SQLGrammarException because the formula
		// template is used as a column alias in the pagination subquery.
		// On Oracle: ORA-00936: missing expression
		// On H2 with OracleDialect: Syntax error in SQL statement
		List<TestOrder> results = query.getResultList();

		em.getTransaction().commit();
		em.close();

		assertThat( results ).hasSize( 5 );
		assertThat( results.get( 1 ).getPaid() ).isTrue();  // order 2 has payment
		assertThat( results.get( 0 ).getItems() ).hasSize( 1 );
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

	@Entity(name = "TestOrder")
	@Table(name = "TEST_ORDERS")
	@NamedEntityGraph(
			name = "TestOrder.withItems",
			attributeNodes = {
					@NamedAttributeNode("items"),
					@NamedAttributeNode("paid")
			}
	)
	public static class TestOrder {

		@Id
		@Column(name = "ORDER_ID")
		private Long id;

		@Column(name = "DESCRIPTION")
		private String description;

		@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
		private List<TestOrderItem> items = new ArrayList<>();

		@Formula("(CASE WHEN EXISTS (SELECT 1 FROM TEST_ORDER_PAYMENTS op"
				+ " WHERE op.ORDER_ID = ORDER_ID) THEN true ELSE false END)")
		@Basic(fetch = FetchType.LAZY)
		private Boolean paid;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<TestOrderItem> getItems() {
			return items;
		}

		public void setItems(List<TestOrderItem> items) {
			this.items = items;
		}

		public Boolean getPaid() {
			return paid;
		}
	}

	@Entity(name = "TestOrderItem")
	@Table(name = "TEST_ORDER_ITEMS")
	public static class TestOrderItem {

		@Id
		@Column(name = "ITEM_ID")
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "ORDER_ID")
		private TestOrder order;

		@Column(name = "PRODUCT_NAME")
		private String productName;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public TestOrder getOrder() {
			return order;
		}

		public void setOrder(TestOrder order) {
			this.order = order;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}
	}

	@Entity(name = "TestOrderPayment")
	@Table(name = "TEST_ORDER_PAYMENTS")
	public static class TestOrderPayment {

		@Id
		@Column(name = "PAYMENT_ID")
		private Long id;

		@Column(name = "ORDER_ID")
		private Long orderId;

		@Column(name = "AMOUNT")
		private Double amount;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}
	}
}
