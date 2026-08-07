
package org.hibernate.bugs.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "PARENT")
@Table(name = "PARENT")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "CODTYPDEM")
public abstract class EntityParent implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "id_relation")
    private String idRelation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_relation", referencedColumnName = "id", insertable = false, updatable = false)
    private EntityRelation relation;

    public EntityRelation getRelation() {
        return relation;
    }

    public void setRelation(EntityRelation requisition) {
        this.relation = requisition;
    }

    public String getIdRelation() {
        return idRelation;
    }

    public void setIdRelation(String idRelation) {
        this.idRelation = idRelation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
