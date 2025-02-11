package org.hibernate.bugs;

import org.hibernate.FetchNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Collections;

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
	void notFoundItemIgnoreUsingFetchOnEntityGraph() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.createNativeQuery("insert into ITEM_WITH_IGNORE(ITEM_ID,ITEM_TYPE) values(12345, 'BOB')").executeUpdate();

		final var graph = entityManager.getEntityGraph("ItemWithIgnore.fetch");
		final var item = entityManager.find(ItemWithIgnore.class, 12345L, Collections.singletonMap("jakarta.persistence.fetchgraph", graph));

		assertThat(item.getItemId()).isEqualTo(12345L);
		assertThat(item.getUnit()).isNull();

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	void notFoundItemExceptionUsingFetchOnEntityGraph() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.createNativeQuery("insert into ITEM_WITH_EXCEPTION(ITEM_ID,ITEM_TYPE) values(12346, 'BOB')").executeUpdate();

		final var graph = entityManager.getEntityGraph("ItemWithException.fetch");
		assertThatThrownBy(() -> {
			entityManager.find(ItemWithException.class, 12346L, Collections.singletonMap("jakarta.persistence.fetchgraph", graph));
		}).isInstanceOf(FetchNotFoundException.class);

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
