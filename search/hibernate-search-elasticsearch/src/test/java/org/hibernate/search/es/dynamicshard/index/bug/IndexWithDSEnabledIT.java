package org.hibernate.search.es.dynamicshard.index.bug;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.cfg.SearchMapping;
import org.hibernate.search.elasticsearch.ElasticsearchQueries;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.engine.spi.QueryDescriptor;
import org.hibernate.search.test.SearchTestBase;
import org.hibernate.search.testsupport.TestForIssue;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by D-YW44CN on 1/07/2016.
 */
public class IndexWithDSEnabledIT extends SearchTestBase {
    @Override
    public Class<?>[] getAnnotatedClasses() {
        return new Class<?>[]{ EntityA.class, EntityB.class };
    }

    @Test
    @TestForIssue(jiraKey = "HSEARCH-NNNNN") // Please fill in the JIRA key of your issue
    @SuppressWarnings("unchecked")
    public void testYourBug() throws IOException {
        Session s = getSessionFromConfig();

        EntityB entityB = new EntityB(1L);
        EntityA entityA = new EntityA(1L, new Date(), "dummy", entityB);
        entityB.setEntityAList(Collections.singletonList(entityA));


        Transaction tx = s.beginTransaction();
        s.persist( entityB );
        s.persist( entityA );
        tx.commit();

        FullTextSession session = Search.getFullTextSession( s );

        QueryBuilder qb = session.getSearchFactory().buildQueryBuilder().forEntity( EntityA.class ).get();
        Query query = qb.keyword().onField("type").matching("dummy").createQuery();

        List<EntityA> result = (List<EntityA>) session.createFullTextQuery( query ).list();

        assertEquals( 1, result.size() );
        assertEquals( 1, (long) result.get( 0 ).getId() );

        s.close();
    }

    @After
    public void deleteTestData() {
        Session s = getSessionFromConfig();
        FullTextSession session = Search.getFullTextSession( s );
        Transaction tx = s.beginTransaction();

        QueryDescriptor query = ElasticsearchQueries.fromJson( "{ 'query': { 'match_all' : {} } }" );
        List<?> result = session.createFullTextQuery( query, EntityA.class ).list();

        for ( Object entity : result ) {
            session.delete( entity );
        }

        result = session.createFullTextQuery( query, EntityB.class ).list();

        for ( Object entity : result ) {
            session.delete( entity );
        }

        tx.commit();
        s.close();
    }

    private Session getSessionFromConfig() {
        SearchMapping mapping = MyAppSearchMappingFactory.getSearchMapping();
        Configuration config = new Configuration();
        config.addAnnotatedClass(EntityB.class)
                .addAnnotatedClass(EntityA.class)
                .getProperties().put("hibernate.search.model_mapping", mapping);
        return config.buildSessionFactory().openSession();
    }

}
