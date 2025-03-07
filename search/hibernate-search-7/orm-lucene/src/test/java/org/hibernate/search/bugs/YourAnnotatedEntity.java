package org.hibernate.search.bugs;

import jakarta.persistence.Transient;
import org.hibernate.search.engine.backend.types.ObjectStructure;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Indexed
public class YourAnnotatedEntity {

	@Id
	@DocumentId
	private Long id;

	@FullTextField(analyzer = "nameAnalyzer")
	private String name;

	@Transient
	@IndexedEmbedded(structure = ObjectStructure.NESTED)
	@IndexingDependency(derivedFrom = @ObjectPath(@PropertyValue(propertyName = "name")))
	private Height height;

	protected YourAnnotatedEntity() {
	}

	public YourAnnotatedEntity(Long id, String name, Long heightValue) {
		this.id = id;
		this.name = name;
		this.height = new Height(heightValue);
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

	public Height getHeight() {
		return height;
	}
}
