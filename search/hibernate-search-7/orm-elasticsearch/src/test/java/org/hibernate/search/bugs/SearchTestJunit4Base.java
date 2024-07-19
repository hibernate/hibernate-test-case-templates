package org.hibernate.search.bugs;

import org.hibernate.cfg.Configuration;

import org.hibernate.testing.AfterClassOnce;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;

public abstract class SearchTestJunit4Base extends BaseCoreFunctionalTestCase {

	private SearchBackendContainer elasticsearchContainer;

	@Override
	protected final void configure(Configuration configuration) {
		super.configure( configuration );

		this.elasticsearchContainer = new SearchBackendContainer();
		configuration.setProperty( "hibernate.search.backend.hosts", elasticsearchContainer.setUp().getHttpHostAddress() );

		configureMore( configuration );
	}

	protected void configureMore(Configuration configuration) {
	}

	@AfterClassOnce
	public void tearDown() {
		try ( SearchBackendContainer esToClose = this.elasticsearchContainer; ) {
			// Nothing to do: we just want resources to get closed.
		}
	}
}
