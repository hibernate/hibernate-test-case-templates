package org.hibernate;

import java.io.Serializable;
import java.util.Set;

public interface IEntityA extends Serializable {
    long getId();

    void setId(long id);

    String getName();

    void setName(String name);

    Set<IEntityB> getBs();

    void setBs(Set<IEntityB> bs);
}
