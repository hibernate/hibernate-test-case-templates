package org.hibernate.bugs.hhh14912.model.xml;

import org.hibernate.bugs.hhh14912.model.AbstractPKEntity;

public class XMLPKEntityLong extends AbstractPKEntity {

    private long pkey;
    private int intVal;

    public long getPkey() {
        return pkey;
    }

    public void setPkey(long pkey) {
        this.pkey = pkey;
    }

    @Override
    public int getIntVal() {
        return intVal;
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
