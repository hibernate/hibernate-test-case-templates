package org.hibernate.bugs;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class MyEntity {
	@EmbeddedId
	private MyEntityId anId;

	private String data;

	public MyEntityId getAnId() {
		return anId;
	}

	public void setAnId(MyEntityId anId) {
		this.anId = anId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
