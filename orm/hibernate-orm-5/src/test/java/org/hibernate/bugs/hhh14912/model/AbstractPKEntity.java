package org.hibernate.bugs.hhh14912.model;

public abstract class AbstractPKEntity implements IPKEntity {

    @Override
    public boolean getBooleanPK() {
        return false;
    }

    @Override
    public Boolean getBooleanWrapperPK() {
        return new Boolean(false);
    }

    @Override
    public long getLongPK() {
        return 0;
    }

    @Override
    public void setLongPK(long pkey) { }
}
