package org.hibernate.bugs;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class EntityBKey implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private EntityA entityA;

    @Column
    private String id;

    public EntityA getEntityA() {
        return entityA;
    }

    public void setEntityA(EntityA entityA) {
        this.entityA = entityA;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
