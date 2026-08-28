package org.hibernate.bugs.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ProductDL {

	private Integer id;
	private String name;
	private Set<ContentDL> contents;

	public ProductDL() {
		contents=new HashSet<>();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public ProductDL setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public ProductDL setName(String name) {
		this.name = name;
		return this;
	}
	
	@OneToMany(mappedBy="product")
	public Set<ContentDL> getContents() {
		return contents;
	}
	public ProductDL setContents(Set<ContentDL> contents) {
		this.contents = contents;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDL other = (ProductDL) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ProductDL [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name : "") + "]";
	}	
}