package org.hibernate.search.bugs;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class AbstractEntity {

	@GeneratedValue(
			strategy  = GenerationType.SEQUENCE,
			generator = "sequence-generator"
	)
	@SequenceGenerator(
			name           = "sequence-generator",
			sequenceName   = "hibernate_sequence",
			allocationSize = 50
	)
	@Id
	private Integer id;

	protected AbstractEntity() {
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
