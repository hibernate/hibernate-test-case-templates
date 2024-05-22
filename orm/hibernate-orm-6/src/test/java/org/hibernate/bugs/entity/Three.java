package org.hibernate.bugs.entity;

import jakarta.persistence.Entity;
import org.hibernate.bugs.entity.AbsThree;
import org.hibernate.bugs.entity.Four;
import org.hibernate.bugs.entity.Two;

@Entity
public class Three extends AbsThree<Two, Four> {
    private String threeConcreteProp;

    public String getThreeConcreteProp() {
        return this.threeConcreteProp;
    }

    public void setThreeConcreteProp(String threeConcreteProp) {
        this.threeConcreteProp = threeConcreteProp;
    }
}