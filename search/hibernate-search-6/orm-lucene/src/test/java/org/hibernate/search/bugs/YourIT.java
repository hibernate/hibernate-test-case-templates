package org.hibernate.search.bugs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import org.junit.Test;

public class YourIT extends SearchTestBase {

	@Override
	public Class<?>[] getAnnotatedClasses() {
		return new Class<?>[]{SubjectEntity.class, UntypedDataEntity.class, PersonEntity.class, MetaData.class,};
	}

	@Test
	public void testYourBug() {
			try ( Session s = getSessionFactory().openSession() ) {
				SubjectEntity yourEntity1 = new SubjectEntity( "string","Hibernate" );
				PersonEntity yourEntity2 = new PersonEntity("string", "John","adress","zip","country");			
				MetaData md = new MetaData();
				md.addData(yourEntity1);
				md.addData(yourEntity2);	
				Transaction tx = s.beginTransaction();
				s.persist( md );
				tx.commit();
			}

			try ( Session session = getSessionFactory().openSession() ) {
				SearchSession searchSession = Search.session( session );

				List<UntypedDataEntity> hits = searchSession.search( UntypedDataEntity.class )
						.where(f -> f.bool()
				        		.must(f.match().fields( "subject" ).matching( "Hibernate"))
				        		.must(f.match().fields( "name" ).matching( "John")))
				        .fetchHits(20);
				System.out.println("#############################################");
				for(UntypedDataEntity e : hits) {
					System.out.println(e.toString());
				}
				System.out.println("##############################################");
				
				assertThat( hits )
						.hasSize( 1 )
						.element( 0 ).extracting( UntypedDataEntity::getId )
						.isEqualTo( 1 );
			}
		}

}
