package org.hibernate.entities;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "query_users", schema = "xxx")
public class QueryUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private QueryUserId id;

	public QueryUser() {

	}

	public QueryUserId getId() {
		return id;
	}

	public void setId(QueryUserId id) {
		this.id = id;
	}
}