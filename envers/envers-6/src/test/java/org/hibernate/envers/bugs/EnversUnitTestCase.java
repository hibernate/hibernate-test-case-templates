/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.bugs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.envers.AuditReader;

import com.example.domain.Pet;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate Envers, using
 * its built-in unit test framework.
 */
public class EnversUnitTestCase extends AbstractEnversTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
			    Pet.class,
		};
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	// Add your tests, using standard JUnit.
	@Test
	public void hhh17484Test() throws Exception {
		Session s = openSession();
		Transaction tx = s.beginTransaction();

		final Pet pet = new Pet();
		pet.setDescription("Meet Whiskers, the enigmatic feline wizard with emerald eyes and a sleek obsidian coat " +
			"adorned with silver constellations. This magical cat enchants with a purr that sparkles like stardust, " +
			"whisking you away to whimsical realms where dreams and reality entwine in a symphony of enchantment.");

		s.persist( pet );

		tx.commit();
		s.close();
	}
}
