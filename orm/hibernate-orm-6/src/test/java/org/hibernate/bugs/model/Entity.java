package org.hibernate.bugs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Id;
import org.hibernate.type.NumericBooleanConverter;

@jakarta.persistence.Entity
public class Entity {
    @Id
    private Long id;
    @Column
    @Convert(converter = NumericBooleanConverter.class)
    private boolean booleanValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
}
