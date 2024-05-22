package org.hibernate.bugs.entityWithPrivateParameterizedProperty;

import jakarta.persistence.Entity;

@Entity
public class AlternativeTwo extends AlternativeAbsTwo<AlternativeOne, AlternativeThree> {
    private String twoConcreteProp;

    public String getTwoConcreteProp() {
        return this.twoConcreteProp;
    }

    public void setTwoConcreteProp(String twoConcreteProp) {
        this.twoConcreteProp = twoConcreteProp;
    }


}

