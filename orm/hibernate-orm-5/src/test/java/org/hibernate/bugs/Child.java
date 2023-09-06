package org.hibernate.bugs;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Child extends Parent {

    @Column
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
