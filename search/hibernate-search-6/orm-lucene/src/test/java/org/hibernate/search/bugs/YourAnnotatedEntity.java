package org.hibernate.search.bugs;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Indexed
public class YourAnnotatedEntity {

	@Id
	@DocumentId
	private Long id;

	@FullTextField(analyzer = "nameAnalyzer")
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
