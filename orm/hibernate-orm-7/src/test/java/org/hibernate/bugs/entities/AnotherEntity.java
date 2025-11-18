package org.hibernate.bugs.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AnotherEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
}