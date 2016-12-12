package org.hibernate.search.es.dynamicshard.index.bug;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.cfg.SearchMapping;

import java.lang.annotation.ElementType;

/**
 * Created by D-YW44CN on 1/07/2016.
 */
public class MyAppSearchMappingFactory {

    public static SearchMapping getSearchMapping() {
        SearchMapping mapping = new SearchMapping();
        mapping.entity(EntityA.class).indexed().indexName("entityA")
                .property("dateType", ElementType.FIELD).dateBridge(Resolution.SECOND)
                .field()
                .store(Store.YES)
                .analyze(Analyze.NO)
                .property("type", ElementType.FIELD)
                .field()
                .store(Store.YES)
                .analyze(Analyze.NO)
                .property("entityB", ElementType.FIELD)
                .indexEmbedded()
                .entity(EntityB.class).indexed()
                .property("id", ElementType.FIELD).documentId().name("arrId")
                .field()
                .store(Store.YES)
                .analyze(Analyze.NO)
        ;

        return mapping;
    }
}
