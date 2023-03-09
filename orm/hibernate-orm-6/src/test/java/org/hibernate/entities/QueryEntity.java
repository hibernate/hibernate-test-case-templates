package org.hibernate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "queries", schema = "xxx")
public class QueryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "queries_id_seq")
	@SequenceGenerator(name = "queries_id_seq", schema = "xxx", sequenceName = "queries_id_seq", allocationSize = 1)
	@Column(name = "id")
	Integer id;

	@Column(name = "model_id", updatable = false)
	Integer modelId;

	@Column(name = "description")
	String description;

	@Column(name = "ts_definition")
	String tsDefinition;

	@Column(name = "definition")
	String queryDefinition;

	@Column(name = "runtime_definition")
	String runtimeDefinition;

	public QueryEntity() {
		this.id = Integer.valueOf(0);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getQueryDefinition() {
		return queryDefinition;
	}

	public void setQueryDefinition(String queryDefinition) {
		this.queryDefinition = queryDefinition;
	}

	public String getTsDefinition() {
		return tsDefinition;
	}

	public void setTsDefinition(String tsDefinition) {
		this.tsDefinition = tsDefinition;
	}

	public String getRuntimeDefinition() {
		return runtimeDefinition;
	}

	public void setRuntimeDefinition(String runtimeDefinition) {
		this.runtimeDefinition = runtimeDefinition;
	}
}
