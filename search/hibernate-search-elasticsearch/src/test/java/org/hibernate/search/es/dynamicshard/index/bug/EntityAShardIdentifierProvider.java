package org.hibernate.search.es.dynamicshard.index.bug;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.lucene.document.Document;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.search.engine.service.spi.ServiceManager;
import org.hibernate.search.hcore.impl.HibernateSessionFactoryService;
import org.hibernate.search.spi.BuildContext;
import org.hibernate.search.store.ShardIdentifierProviderTemplate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by D-YW44CN on 21/06/2016.
 */
public class EntityAShardIdentifierProvider extends ShardIdentifierProviderTemplate {



    @Override
    protected Set<String> loadInitialShardNames(Properties properties, BuildContext buildContext) {
        ServiceManager serviceManager = buildContext.getServiceManager();
        SessionFactory sessionFactory = (SessionFactory) serviceManager.requestService(
                HibernateSessionFactoryService.class).getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Criteria initialShardsCriteria = session.createCriteria(EntityA.class);
            initialShardsCriteria.setProjection( Projections.distinct(Property.forName("dateType")));

            @SuppressWarnings("unchecked")
            List<String> initialTypes = initialShardsCriteria.list();
            Collection returnList = CollectionUtils.collect(initialTypes, new Transformer() {
                @Override
                public Object transform(Object o) {
                    String inputString = ((Timestamp) o).toString();
                    String result = inputString.substring(0, inputString.indexOf("-"));
                    return result;
                }
            });
            Set<String> returnSet = new HashSet<String>();
            returnSet.addAll(returnList);
            return returnSet;
        }
        finally {
            session.close();
        }
    }

    @Override
    public String getShardIdentifier(Class<?> entityType, Serializable serializable, String s, Document document) {
        if ( entityType.equals(EntityA.class) ) {
            String type = document.getField("dateType").stringValue();
            type = type.substring(0, type.indexOf("-"));
            addShard(type);
            return type;
        }
        throw new RuntimeException("EntityA expected but found " + entityType);
    }
}
