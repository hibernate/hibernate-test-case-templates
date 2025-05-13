
package org.hibernate.bugs.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "RELATION")
@Table(name = "RELATION")
public class EntityRelation {
    @Id
    @Column(name = "id")
    private String id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relation")
    private List<EntityParent> parents;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<EntityParent> getParents() {
        return parents;
    }

    public void setParents(List<EntityParent> demandes) {
        this.parents = demandes;
    }
}
