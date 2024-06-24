package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Persistence;

import jakarta.persistence.TypedQuery;
import java.util.List;
import org.hibernate.annotations.TenantId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh18291Test() throws Exception {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    InvoiceBE invoice = entityManager.merge(new InvoiceBE().setId(1L).setRemoved(false));
    PaidInvoiceBE paidInvoice = entityManager.merge(new PaidInvoiceBE().setId(1).setInvoice(invoice));

    TypedQuery<Boolean> query = entityManager.createQuery("""
        SELECT i.removed = false
          AND (SELECT count(*) = 0
                FROM PaidInvoiceBE pi
                WHERE pi.invoice.id = i.id)
        FROM InvoiceBE i
        WHERE i.id = :invoiceId
        """, Boolean.class);
    query.setParameter("invoiceId", 1L);
    Boolean result = query.getSingleResult();
    System.out.println(result);

    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
