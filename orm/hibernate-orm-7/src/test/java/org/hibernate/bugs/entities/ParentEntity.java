package org.hibernate.bugs.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ParentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ChildEntity> children = new ArrayList<>();

	public void addChild(ChildEntity child) {
		child.setParent(this);
		children.add(child);
	}

	public void removeChild(ChildEntity child) {
		child.setParent(null);
		children.remove(child);
	}

	public void replaceChildren(List<ChildEntity> nextChildren) {
		var prev = new ArrayList<>(children);
		prev.forEach(this::removeChild);
		nextChildren.forEach(this::addChild);
	}

	@Override
	public String toString() {
		return "ParentEntity [id=" + id + ", children=" + children + "]";
	}

	public String getId() {
		return id;
	}

	public List<ChildEntity> getChildren() {
		return children;
	}

}