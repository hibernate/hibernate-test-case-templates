package org.hibernate.bugs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Loader;

@Entity(name = "parent")
@Table(name = "PARENT")
public class ParentEntity extends AbstractEntity {

	private static final long serialVersionUID = -4862532469556005967L;

	@Loader(namedQuery = "loadNestedOrdered")
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ManyToManyEntity> children = new ArrayList<>();

	public List<ManyToManyEntity> getChildren() {
		return children;
	}

	public void setChildren(List<ManyToManyEntity> children) {
		this.children = children;
	}

	public void addNested(NestedEntity nested) {
		ManyToManyEntity m2m = new ManyToManyEntity(this, nested);
		children.add(m2m);
		nested.getParents().add(m2m);
	}

	public void removeNested(NestedEntity nested) {
		ManyToManyEntity m2m = new ManyToManyEntity(this, nested);
		children.remove(m2m);
		nested.getParents().remove(m2m);
		m2m.setNested(null);
		m2m.setParent(null);
	}

}
