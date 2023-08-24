package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class EntityA {

    @Id
    @Column
    private int id;

    @OneToMany(targetEntity = EntityB.class, mappedBy = "key.entityA")
    private List<EntityB> entitiesB;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<EntityB> getEntitiesB() {
        return entitiesB;
    }

    public void setEntitiesB(List<EntityB> entitiesB) {
        this.entitiesB = entitiesB;
    }
}
