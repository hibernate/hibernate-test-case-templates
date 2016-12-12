/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import org.hibernate.cfg.Environment;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.jpa.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.spi.Bootstrap;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.PersistenceUnitTransactionType;
import java.net.URL;
import java.util.*;

/**
 * This template demonstrates how to develop a standalone test case for Hibernate ORM using the JPA specification.
 */
public class ORMStandaloneJpaUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

    // Add your entities here.
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[0];
	}

    // If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
    protected String[] getMappings() {
        return new String[0];
    }

	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {

	}

    @Before
    public void setup() {
        entityManagerFactory =  Bootstrap.getEntityManagerFactoryBuilder(
                buildPersistenceUnitDescriptor(),
                buildSettings()
        ).build();
    }

	private PersistenceUnitDescriptor buildPersistenceUnitDescriptor() {
		return new TestingPersistenceUnitDescriptorImpl( getClass().getSimpleName() );
	}

	public static class TestingPersistenceUnitDescriptorImpl implements PersistenceUnitDescriptor {
		private final String name;

		public TestingPersistenceUnitDescriptorImpl(String name) {
			this.name = name;
		}

		@Override
		public URL getPersistenceUnitRootUrl() {
			return null;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getProviderClassName() {
			return HibernatePersistenceProvider.class.getName();
		}

		@Override
		public boolean isUseQuotedIdentifiers() {
			return false;
		}

		@Override
		public boolean isExcludeUnlistedClasses() {
			return false;
		}

		@Override
		public PersistenceUnitTransactionType getTransactionType() {
			return null;
		}

		@Override
		public ValidationMode getValidationMode() {
			return null;
		}

		@Override
		public SharedCacheMode getSharedCacheMode() {
			return null;
		}

		@Override
		public List<String> getManagedClassNames() {
			return null;
		}

		@Override
		public List<String> getMappingFileNames() {
			return null;
		}

		@Override
		public List<URL> getJarFileUrls() {
			return null;
		}

		@Override
		public Object getNonJtaDataSource() {
			return null;
		}

		@Override
		public Object getJtaDataSource() {
			return null;
		}

		@Override
		public Properties getProperties() {
			return null;
		}

		@Override
		public ClassLoader getClassLoader() {
			return null;
		}

		@Override
		public ClassLoader getTempClassLoader() {
			return null;
		}

		@Override
		public void pushClassTransformer(Collection<String> entityClassNames) {
		}
	}

	@SuppressWarnings("unchecked")
	private Map buildSettings() {
		Map settings = getConfig();
		String[] mappings = getMappings();
		if ( mappings != null ) {
			settings.put( AvailableSettings.HBXML_FILES, StringHelper.join( ",", mappings ) );
		}

		settings.put( org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO, "update" );
		settings.put(org.hibernate.cfg.AvailableSettings.SHOW_SQL, "true");
		settings.put(org.hibernate.cfg.AvailableSettings.FORMAT_SQL, "true");

		settings.put( org.hibernate.cfg.AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "true" );
		return settings;
	}

	private Map getConfig() {
		Map<Object, Object> config = Environment.getProperties();
		ArrayList<Class> classes = new ArrayList<Class>();

		classes.addAll( Arrays.asList( getAnnotatedClasses() ) );
		config.put( AvailableSettings.LOADED_CLASSES, classes );
		for ( Map.Entry<Class, String> entry : getCachedClasses().entrySet() ) {
			config.put( AvailableSettings.CLASS_CACHE_PREFIX + "." + entry.getKey().getName(), entry.getValue() );
		}
		for ( Map.Entry<String, String> entry : getCachedCollections().entrySet() ) {
			config.put( AvailableSettings.COLLECTION_CACHE_PREFIX + "." + entry.getKey(), entry.getValue() );
		}
		if ( getEjb3DD().length > 0 ) {
			ArrayList<String> dds = new ArrayList<String>();
			dds.addAll( Arrays.asList( getEjb3DD() ) );
			config.put( AvailableSettings.XML_FILE_NAMES, dds );
		}
		return config;
	}

	private Map<Class, String> getCachedClasses() {
		return new HashMap<Class, String>();
	}

    private Map<String, String> getCachedCollections() {
		return new HashMap<String, String>();
	}

    private String[] getEjb3DD() {
		return new String[] { };
	}
}
