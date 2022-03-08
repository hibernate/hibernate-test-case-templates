package org.hibernate.bugs.hhh15113.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(HHH15113Entity.class)
public class HHH15113Entity_ {
    public static volatile SingularAttribute<HHH15113Entity, String> KeyString;
    public static volatile SingularAttribute<HHH15113Entity, String> itemString1;
    public static volatile SingularAttribute<HHH15113Entity, Integer> itemInteger1;
}
