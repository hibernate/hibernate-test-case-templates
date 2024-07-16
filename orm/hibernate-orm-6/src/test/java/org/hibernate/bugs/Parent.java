package org.hibernate.bugs;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Parent {

	@Id
	private Long id;

	private String name;

	@OneToMany(orphanRemoval = true, mappedBy = "parent")
	private List<Child1> child1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Child1> getChild1() {
		return child1;
	}

	public void setChild1(List<Child1> child1) {
		this.child1 = child1;
	}

}
