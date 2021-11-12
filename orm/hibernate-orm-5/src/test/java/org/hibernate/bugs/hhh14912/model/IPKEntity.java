package org.hibernate.bugs.hhh14912.model;

public interface IPKEntity {

    public boolean getBooleanPK();
    public Boolean getBooleanWrapperPK();

    public int getIntVal();
    public void setIntVal(int intVal);

    public long getLongPK();
    public void setLongPK(long pkey);
}
