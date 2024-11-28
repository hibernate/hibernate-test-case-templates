package org.hibernate.pojos;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(HitCount.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class HitCount_ {

	public static volatile SingularAttribute<HitCount, Catalogue> catalogue;

	public static volatile SingularAttribute<HitCount, String> userName;

	public static volatile SingularAttribute<HitCount, String> id;

	public static volatile EntityType<HitCount> class_;

	public static final String CATALOGUE = "catalogue";
	public static final String USER_NAME = "userName";
	public static final String ID = "id";

}
