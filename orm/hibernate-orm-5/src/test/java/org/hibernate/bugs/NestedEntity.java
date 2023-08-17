package org.hibernate.bugs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "nested")
@Table(name = "NESTED")
public class NestedEntity extends AbstractEntity {

	private static final long serialVersionUID = 3851500280357761256L;

	@OneToMany(mappedBy = "nested", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ManyToManyEntity> parents = new ArrayList<>();

	private String name;

	public List<ManyToManyEntity> getParents() {
		return parents;
	}

	public void setParents(List<ManyToManyEntity> parents) {
		this.parents = parents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
