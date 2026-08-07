package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Entity1 {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@OneToOne
	private Entity3 entity3;

	public Entity1(Long id, Entity3 entity3) {
		this.id = id;
		this.entity3 = entity3;
	}

	public Entity1() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Entity3 getEntity3() {
		return entity3;
	}

	public void setEntity3(Entity3 entity3) {
		this.entity3 = entity3;
	}

	@Override
	public String toString() {
		return "Entity1{" +
				"id=" + id +
				", name='" + name + '\'' +
				", entity3=" + entity3 +
				'}';
	}
}
