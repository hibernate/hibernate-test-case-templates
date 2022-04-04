/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
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
