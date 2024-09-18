package org.hibernate.bugs;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Parent {

	@Id
	private Long id;

	@OneToMany(mappedBy = "parent")
	@LazyCollection(LazyCollectionOption.FALSE)
	List<ManyToMany> children = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ManyToMany> getChildren() {
		return children;
	}

	public void setChildren(List<ManyToMany> children) {
		this.children = children;
	}
}
