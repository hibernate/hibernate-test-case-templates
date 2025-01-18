package org.hibernate.search.bugs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.jupiter.api.Test;

public class YourIT extends SearchTestBase {

	@Override
	public Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] { YourAnnotatedEntity.class };
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testYourBug() {
		try ( Session s = getSessionFactory().openSession() ) {
			YourAnnotatedEntity yourEntity1 = new YourAnnotatedEntity( 1L, "Jane Smith" );
			YourAnnotatedEntity yourEntity2 = new YourAnnotatedEntity( 2L, "John Doe" );

			Transaction tx = s.beginTransaction();
			s.persist( yourEntity1 );
			s.persist( yourEntity2 );
			tx.commit();
		}

		try ( Session s = getSessionFactory().openSession() ) {
			FullTextSession session = Search.getFullTextSession( s );
			QueryBuilder qb = session.getSearchFactory().buildQueryBuilder().forEntity( YourAnnotatedEntity.class ).get();
			Query query = qb.keyword().onField( "name" ).matching( "Jane Smith" ).createQuery();

			List<YourAnnotatedEntity> result = (List<YourAnnotatedEntity>) session.createFullTextQuery( query ).list();
			assertThat( result.size() ).isEqualTo( 1 );
			assertThat( result.get( 0 ).getId() ).isEqualTo( 1L );
		}
	}

}
