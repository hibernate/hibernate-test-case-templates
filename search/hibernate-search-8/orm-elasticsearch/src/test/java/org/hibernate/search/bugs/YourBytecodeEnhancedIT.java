package org.hibernate.search.bugs;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import org.hibernate.testing.bytecode.enhancement.BytecodeEnhancerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Use this template <b>only</b> if you need Hibernate ORM bytecode enhancement to reproduce your issue.
 */
@RunWith(BytecodeEnhancerRunner.class) // This runner enables bytecode enhancement for your test.
public class YourBytecodeEnhancedIT extends SearchTestJunit4Base {

	@Override
	public Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] { YourAnnotatedEntity.class };
	}

	@Test
	public void testYourBug() {
		try ( Session s = openSession() ) {
			YourAnnotatedEntity yourEntity1 = new YourAnnotatedEntity( 1L, "Jane Smith" );
			YourAnnotatedEntity yourEntity2 = new YourAnnotatedEntity( 2L, "John Doe" );

			Transaction tx = s.beginTransaction();
			s.persist( yourEntity1 );
			s.persist( yourEntity2 );
			tx.commit();
		}

		try ( Session session = openSession() ) {
			SearchSession searchSession = Search.session( session );

			List<YourAnnotatedEntity> hits = searchSession.search( YourAnnotatedEntity.class )
					.where( f -> f.match().field( "name" ).matching( "smith" ) )
					.fetchHits( 20 );

			assertThat( hits )
					.hasSize( 1 )
					.element( 0 ).extracting( YourAnnotatedEntity::getId )
					.isEqualTo( 1L );
		}
	}

	@Test
	public void testBytecodeEnhancement() {
		assertThat( YourAnnotatedEntity.class.getDeclaredMethods() )
				.extracting( Method::getName )
				.anyMatch( name -> name.startsWith( "$$_hibernate_" ) );
	}

}
