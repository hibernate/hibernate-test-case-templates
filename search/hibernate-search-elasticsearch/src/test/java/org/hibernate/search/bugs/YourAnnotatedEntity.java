package org.hibernate.search.bugs;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Indexed
public class YourAnnotatedEntity {

	@Id
	@DocumentId
	private Long id;

	@Field
	private String name;

	protected YourAnnotatedEntity() {
	}

	public YourAnnotatedEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
