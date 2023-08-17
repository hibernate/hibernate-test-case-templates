package org.hibernate.bugs;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity(name = "manytomanyentity")
@Table(name = "MANY_TO_MANY_ENTITY")
@IdClass(ManyToManyEntity.class)
@NamedNativeQueries(@NamedNativeQuery(name = "loadNestedOrdered", query = "select * from NESTED join MANY_TO_MANY_ENTITY on NESTED.id = MANY_TO_MANY_ENTITY.nested_id where MANY_TO_MANY_ENTITY.parent_id = ? order by NESTED.name"))
public class ManyToManyEntity implements Serializable {

	public ManyToManyEntity() {

	}

	public ManyToManyEntity(ParentEntity parent, NestedEntity nested) {
		super();
		this.parent = parent;
		this.nested = nested;
	}

	private static final long serialVersionUID = 7854934197774679787L;

	@Id
	@ManyToOne
	private ParentEntity parent;

	@Id
	@ManyToOne
	private NestedEntity nested;

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public NestedEntity getNested() {
		return nested;
	}

	public void setNested(NestedEntity nested) {
		this.nested = nested;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ManyToManyEntity that = (ManyToManyEntity) o;
		return Objects.equals(parent, that.parent) && Objects.equals(nested, that.nested);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parent, nested);
	}

}
