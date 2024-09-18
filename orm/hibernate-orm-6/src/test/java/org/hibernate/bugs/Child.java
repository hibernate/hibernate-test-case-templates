package org.hibernate.bugs;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;

@Entity
public class Child {

	@Id
	private Long id;

	@OneToMany(mappedBy = "child")
	@OrderColumn
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
