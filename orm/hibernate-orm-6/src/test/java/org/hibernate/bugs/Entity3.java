package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Entity3 extends Entity2 {

	@Column
	private String surname;

	public Entity3() {

	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Entity3(String surname) {
		this.surname = surname;
	}

	public Entity3(Long id, String name, String surname) {
		super(id, name);
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Entity3{" +
				"surname='" + surname + '\'' +
				'}';
	}
}
