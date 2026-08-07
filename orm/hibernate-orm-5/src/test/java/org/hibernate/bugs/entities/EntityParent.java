
package org.hibernate.bugs.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "PARENT")
@Table(name = "PARENT")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "DISCRIMINATOR")
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
