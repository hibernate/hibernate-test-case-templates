package org.hibernate.search.bugs;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.junit.After;
import org.junit.Before;

public abstract class SearchTestBase {
	
	private SessionFactory sessionFactory;
	
	@Before
	public void setUp() {
		StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
		MetadataSources ms = new MetadataSources( registryBuilder.build() );
		Class<?>[] annotatedClasses = getAnnotatedClasses();
		if ( annotatedClasses != null ) {
			for ( Class<?> entity : annotatedClasses ) {
				ms.addAnnotatedClass( entity );
			}
		}

		Metadata metadata = ms.buildMetadata();

		final SessionFactoryBuilder sfb = metadata.getSessionFactoryBuilder();
		this.sessionFactory = sfb.build();
	}
	
	@After
	public void tearDown() {
		this.sessionFactory.close();
	}

	protected abstract Class<?>[] getAnnotatedClasses();
	
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
