package org.hibernate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
public class EntityPlain {

    @Id
    @GeneratedValue
    private long id;

    private List<String> listString;

    private List<Integer> listInteger;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getListString() {
        return listString;
    }

    public void setListString(List<String> listA) {
        this.listString = listA;
    }

    public List<Integer> getListInteger() {
        return listInteger;
    }

    public void setListInteger(List<Integer> listB) {
        this.listInteger = listB;
    }
}
