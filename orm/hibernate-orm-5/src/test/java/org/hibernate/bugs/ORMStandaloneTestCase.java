package org.hibernate.bugs;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM.  Although this is perfectly
 * acceptable as a reproducer, usage of ORMUnitTestCase is preferred!
 */
public class ORMStandaloneTestCase {

	private SessionFactory sf;

	@Before
	public void setup() {
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
			// Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
			.applySetting( "hibernate.show_sql", "true" )
			.applySetting( "hibernate.format_sql", "true" )
			.applySetting( "hibernate.hbm2ddl.auto", "create" );

		Metadata metadata = new MetadataSources( srb.build() )
		// Add your entities here.
		//	.addAnnotatedClass( Foo.class )
			.addAnnotatedClass(EntityA.class)
			.addAnnotatedClass(EntityB.class)
			.buildMetadata();

		sf = metadata.buildSessionFactory();
		setupFixture();
	}

	// Add your tests, using standard JUnit.

	@Test
	public void hhh13530LeftOuterJoinTest() {
		final EntityManager entityManager = sf.createEntityManager();

		entityManager.getTransaction().begin();

		// test: get only the non removed entityBs for the left join
		List<EntityB> entityBs = entityManager.createQuery("select distinct b from EntityB b left outer join fetch b.entityAs _as", EntityB.class).getResultList();
		Assert.assertEquals(1, entityBs.size());
		Assert.assertEquals(1, (long)entityBs.get(0).getEntityAs().size());
		Assert.assertEquals(2L, (long)entityBs.get(0).getEntityAs().iterator().next().getId());
		Assert.assertFalse(entityBs.get(0).getEntityAs().iterator().next().isRemoved());

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void hhh13530InnerTest() {
		final EntityManager entityManager = sf.createEntityManager();

		entityManager.getTransaction().begin();

		// test: get only the non removed entityBs for the inner join
		List<EntityB> entityBs = entityManager.createQuery("select distinct b from EntityB b inner join fetch b.entityAs _as", EntityB.class).getResultList();
		Assert.assertEquals(1, entityBs.size());
		Assert.assertEquals(1, (long)entityBs.get(0).getEntityAs().size());
		Assert.assertEquals(2L, (long)entityBs.get(0).getEntityAs().iterator().next().getId());
		Assert.assertFalse(entityBs.get(0).getEntityAs().iterator().next().isRemoved());

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private void setupFixture() {
		final EntityManager entityManager = sf.createEntityManager();

		entityManager.getTransaction().begin();

		// saving fixture
		{
			EntityB entityB = new EntityB();
			entityB.setId(1L);
			entityB.setEntityAs(new HashSet<>());
			EntityA entityA1 = new EntityA();
			entityA1.setId(2L);
			entityA1.setRemoved(false);
			entityA1.setEntityBs(Collections.singleton(entityB));
			entityB.getEntityAs().add(entityA1);
			EntityA entityA2 = new EntityA();
			entityA2.setId(3L);
			entityA2.setRemoved(true);
			entityA2.setEntityBs(Collections.singleton(entityB));
			entityB.getEntityAs().add(entityA2);
			entityManager.persist(entityB);
			entityManager.persist(entityA1);
			entityManager.persist(entityA2);
		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
