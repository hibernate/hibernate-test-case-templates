package org.hibernate.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "visualization", schema = "xxx")
public class Visualization {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visualization_id_seq")
	@SequenceGenerator(name = "visualization_id_seq", schema = "studio", sequenceName = "visualization_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "query_id", updatable = false)
	private Integer queryId;

	@Column(name = "definition")
	private String definition;

	@Column(name = "owner_id")
	private UUID ownerId;

	public Visualization() {
		this.id = Integer.valueOf(0);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}
}
