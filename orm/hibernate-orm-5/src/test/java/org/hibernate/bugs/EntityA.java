package org.hibernate.bugs;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "entity_a")
public class EntityA {
    private Long id;
    private Set<EntityB> entityBs = new HashSet<>();
    private boolean removed;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "removed")
    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @ManyToMany
    @JoinTable(name = "entity_a_b", joinColumns = {@JoinColumn(name = "aid")}, inverseJoinColumns = {@JoinColumn(name = "bid")})
    public Set<EntityB> getEntityBs() {
        return entityBs;
    }

    public void setEntityBs(Set<EntityB> entityBs) {
        this.entityBs = entityBs;
    }
}
