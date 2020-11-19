package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.bugs.model.Asset;
import org.hibernate.bugs.model.AssetAssetTypeAttribute;
import org.hibernate.bugs.model.AssetTypeAttribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

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
	public void hhh14340Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Asset asset = new Asset();
		asset.setId(1L);
		asset.setTenantId(2L);
		AssetTypeAttribute assetTypeAttribute = new AssetTypeAttribute();
		assetTypeAttribute.setId(3L);
		assetTypeAttribute.setName("TestAttribute");

		AssetAssetTypeAttribute assetAssetTypeAttribute = new AssetAssetTypeAttribute();

		assetAssetTypeAttribute.setAssetTypeAttributeId(assetTypeAttribute.getId());
		assetAssetTypeAttribute.setAsset(asset);
		asset.setAssetAssetTypeAttributes(new HashSet<>());
		asset.getAssetAssetTypeAttributes().add(assetAssetTypeAttribute);

		entityManager.persist(asset);

		for (AssetAssetTypeAttribute assetAssetTypeAttribute1 : asset.getAssetAssetTypeAttributes()) {
			entityManager.persist(assetAssetTypeAttribute1);
		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
