package org.hibernate.bugs.entityWithPrivateParameterizedProperty;

import jakarta.persistence.Entity;

@Entity
public class AlternativeThree extends AlternativeAbsThree<AlternativeTwo, AlternativeFour> {
    private String threeConcreteProp;

    public String getThreeConcreteProp() {
        return this.threeConcreteProp;
    }

    public void setThreeConcreteProp(String threeConcreteProp) {
        this.threeConcreteProp = threeConcreteProp;
    }
}