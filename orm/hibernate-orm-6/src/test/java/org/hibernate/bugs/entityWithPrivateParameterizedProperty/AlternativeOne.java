package org.hibernate.bugs.entityWithPrivateParameterizedProperty;

import jakarta.persistence.Entity;

@Entity
public class AlternativeOne extends AlternativeAbsOne<AlternativeTwo> {
    private String oneConcreteProp;

    public String getOneConcreteProp() {
        return this.oneConcreteProp;
    }

    public void setOneConcreteProp(String oneConcreteProp) {
        this.oneConcreteProp = oneConcreteProp;
    }
}
