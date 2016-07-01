package org.hibernate.search.es.dynamicshard.index.bug;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by D-YW44CN on 1/07/2016.
 */
@Entity
public class EntityB {
    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entityB")
    private List<EntityA> entityAList;

    public EntityB(Long id) {
        this.id = id;
    }

    public EntityB() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EntityA> getEntityAList() {
        return entityAList;
    }

    public void setEntityAList(List<EntityA> entityAList) {
        this.entityAList = entityAList;
    }
}
