package org.hibernate.bugs.hhh14912.model.annotation;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.bugs.hhh14912.model.AbstractPKEntity;

@Entity
public class PKEntityLong extends AbstractPKEntity {

    @Id
    private long pkey;
    private int intVal;

    public long getPkey() {
        return this.pkey;
    }

    public void setPkey(long pkey) {
        this.pkey = pkey;
    }

    @Override
    public int getIntVal() {
        return this.intVal;
    }

    @Override
    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    @Override
    public long getLongPK() {
        return getPkey();
    }

    @Override
    public void setLongPK(long pkey) {
        setPkey(pkey);
    }
}
