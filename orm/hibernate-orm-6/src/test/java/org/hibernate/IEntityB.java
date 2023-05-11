package org.hibernate;

import java.io.Serializable;
import java.util.Set;

public interface IEntityB extends Serializable {
    IEntityA getA();

    void setA(IEntityA a);

    long getId();

    void setId(long id);

    String getSomething();

    void setSomething(String something);

    Set<IEntityC> getCs();

    void setCs(Set<IEntityC> cs);
}
