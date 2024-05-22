package org.hibernate.bugs.entity;

import jakarta.persistence.Entity;
import org.hibernate.bugs.entity.AbsTwo;
import org.hibernate.bugs.entity.One;
import org.hibernate.bugs.entity.Three;

@Entity
public class Two extends AbsTwo<One, Three> {
    private String twoConcreteProp;

    public String getTwoConcreteProp() {
        return this.twoConcreteProp;
    }

    public void setTwoConcreteProp(String twoConcreteProp) {
        this.twoConcreteProp = twoConcreteProp;
    }


}

