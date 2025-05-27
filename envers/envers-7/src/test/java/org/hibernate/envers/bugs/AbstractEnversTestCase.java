package org.hibernate.envers.bugs;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;

/**
 * @author Chris Cranford
 */
public abstract class AbstractEnversTestCase extends BaseCoreFunctionalTestCase {
	private AuditReader auditReader;

	protected AuditReader getAuditReader() {
		if ( auditReader == null || session == null || !session.isOpen() ) {
			auditReader = AuditReaderFactory.get( openSession() );
		}
		return auditReader;
	}

}
