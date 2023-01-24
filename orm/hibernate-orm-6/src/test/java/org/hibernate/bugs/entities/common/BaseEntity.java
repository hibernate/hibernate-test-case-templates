package org.hibernate.bugs.entities.common;


public abstract class BaseEntity<T> {

    public abstract T getId();


    @Override
    public String toString() {
        return this.getId().toString();
    }

}
