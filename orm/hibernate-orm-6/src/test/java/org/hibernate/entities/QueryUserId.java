package org.hibernate.entities;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class QueryUserId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "query_id")
	private Integer queryId;

	@Column(name = "user_id")
	private UUID userId;

	public QueryUserId() {
		// Default construction needed for JPA
	}

	public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}