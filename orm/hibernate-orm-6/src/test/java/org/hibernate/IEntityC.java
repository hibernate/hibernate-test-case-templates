package org.hibernate;

import java.io.Serializable;

public interface IEntityC extends Serializable {
    IEntityB getB();

    void setB(IEntityB b);

    long getId();

    void setId(long id);

    String getName();

    void setName(String name);
}
