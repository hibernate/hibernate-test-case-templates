package org.hibernate.bugs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(schema = "", name = "address")
public class AddressEntity extends BaseEntity {
	private String street;
	private String city;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}