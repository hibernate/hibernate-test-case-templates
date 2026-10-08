package org.hibernate.reactive.bugs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * An entity to starts with.
 */
@Entity
public class MyEntity {

	@Id
	private Long id;
	private String name;

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

	@Override
	public String toString() {
		return "MyEntity(" + id + ":" + name + ")";
	}
}
