package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class EntityB {

    @EmbeddedId
    private EntityBKey key;

    @Column
    private String otherColumn;

    public EntityBKey getKey() {
        return key;
    }

    public void setKey(EntityBKey key) {
        this.key = key;
    }

    public String getOtherColumn() {
        return otherColumn;
    }

    public void setOtherColumn(String otherColumn) {
        this.otherColumn = otherColumn;
    }
}
