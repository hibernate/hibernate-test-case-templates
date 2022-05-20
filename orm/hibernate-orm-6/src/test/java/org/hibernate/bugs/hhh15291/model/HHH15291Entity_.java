package org.hibernate.bugs.hhh15291.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(HHH15291Entity.class)
public class HHH15291Entity_ {
    public static volatile SingularAttribute<HHH15291Entity, String> KeyString;
    public static volatile SingularAttribute<HHH15291Entity, String> itemString1;
    public static volatile SingularAttribute<HHH15291Entity, String> itemString2;
    public static volatile SingularAttribute<HHH15291Entity, String> itemString3;
    public static volatile SingularAttribute<HHH15291Entity, String> itemString4;
    public static volatile SingularAttribute<HHH15291Entity, Integer> itemInteger1;
}
