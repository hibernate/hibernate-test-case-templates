package org.hibernate.search.es.dynamicshard.index.bug;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by D-YW44CN on 1/07/2016.
 */
@Entity
public class EntityA {
    @Id
    private Long id;

    private Date dateType;

    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "B_IDE", nullable = false)
    private EntityB entityB;

    public EntityA(Long id, Date date, String val, EntityB entityB) {
        this.id = id;
        this.dateType = date;
        this.entityB = entityB;
        this.type = val;
    }

    public EntityA() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateType() {
        return dateType;
    }

    public void setDateType(Date dateType) {
        this.dateType = dateType;
    }

    public EntityB getEntityB() {
        return entityB;
    }

    public void setEntityB(EntityB entityB) {
        this.entityB = entityB;
    }
}
