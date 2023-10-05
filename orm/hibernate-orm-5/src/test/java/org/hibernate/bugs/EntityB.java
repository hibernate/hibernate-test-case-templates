package org.hibernate.bugs;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Table(name = "ENTITY_B")
@Entity
public class EntityB {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "ENTITY_A_ID", referencedColumnName = "ID")
    private EntityA entityA;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entityB", fetch = FetchType.EAGER)
    private List<EntityC> entityCS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityA getEntityA() {
        return entityA;
    }

    public void setEntityA(EntityA entityA) {
        this.entityA = entityA;
    }

    public List<EntityC> getEntityCS() {
        return entityCS;
    }

    public void setEntityCS(List<EntityC> entityCS) {
        this.entityCS = entityCS;
    }
}
