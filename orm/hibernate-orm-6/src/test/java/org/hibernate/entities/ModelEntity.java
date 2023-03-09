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
@Table(name = "models", schema = "xxx")
public class ModelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "models_id_seq")
	@SequenceGenerator(name = "models_id_seq", schema = "xxx", sequenceName = "models_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "tenant_id", updatable = false)
	private UUID tenantId;

	@Column(name = "name", updatable = false)
	private String name;

	@Column(name = "uri", updatable = false)
	private String uri;

	public ModelEntity() {
		this.id = Integer.valueOf(0);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getTenantId() {
		return tenantId;
	}

	public void setTenantId(UUID tenantId) {
		this.tenantId = tenantId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
