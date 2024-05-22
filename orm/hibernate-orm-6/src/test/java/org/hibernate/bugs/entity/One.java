package org.hibernate.bugs.entity;

import jakarta.persistence.Entity;
import org.hibernate.bugs.entity.AbsOne;
import org.hibernate.bugs.entity.Two;

@Entity
public class One extends AbsOne<Two> {
    private String oneConcreteProp;

    public String getOneConcreteProp() {
        return this.oneConcreteProp;
    }

    public void setOneConcreteProp(String oneConcreteProp) {
        this.oneConcreteProp = oneConcreteProp;
    }
}
