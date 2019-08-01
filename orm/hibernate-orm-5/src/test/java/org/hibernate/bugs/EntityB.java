package org.hibernate.bugs;

import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "entity_b")
public class EntityB {
    private Long id;
    private Set<EntityA> entityAs = new HashSet<>();

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(mappedBy = "entityBs")
    @Where(clause = "removed is false")
    public Set<EntityA> getEntityAs() {
        return entityAs;
    }

    public void setEntityAs(Set<EntityA> entityAs) {
        this.entityAs = entityAs;
    }
}
