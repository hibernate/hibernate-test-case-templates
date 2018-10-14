package org.hibernate.bugs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6330307788112790008L;
	@Id
	@Column(name = "id", nullable = false, insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EntityIdSeqGenerator")
	@SequenceGenerator(name = "EntityIdSeqGenerator", allocationSize = 1, sequenceName = "ID_SEQ", initialValue = 1)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
