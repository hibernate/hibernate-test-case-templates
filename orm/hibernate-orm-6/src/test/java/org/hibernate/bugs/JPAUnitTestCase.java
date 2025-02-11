package org.hibernate.bugs;

import org.hibernate.FetchNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	@Test
	void notFoundItemWithIgnore() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.createNativeQuery("insert into ITEM_WITH_IGNORE(ITEM_ID,ITEM_TYPE) values(12345, 'BOB')").executeUpdate();

		final var item = entityManager.find(ItemWithIgnore.class, 12345L);
		assertThat(item.getItemId()).isEqualTo(12345L);
		assertThat(item.getUnit()).isNull();

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	void notFoundItemWithException() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.createNativeQuery("insert into ITEM_WITH_EXCEPTION(ITEM_ID,ITEM_TYPE) values(12345, 'BOB')").executeUpdate();

		assertThatThrownBy(() -> entityManager.find(ItemWithException.class, 12345L))
				.isInstanceOf(FetchNotFoundException.class)
				.hasMessage("Entity `org.hibernate.bugs.UnitReferenceCode` with identifier value `ReferenceCode.Pk(domain=UNIT, code=BOB)` does not exist");

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
