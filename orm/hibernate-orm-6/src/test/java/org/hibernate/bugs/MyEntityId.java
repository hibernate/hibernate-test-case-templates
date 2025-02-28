package org.hibernate.bugs;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class MyEntityId {
	private Long id;
	private String subId; // 추가 필드

	public MyEntityId() {}

	public MyEntityId(Long id, String subId) {
		this.id = id;
		this.subId = subId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MyEntityId that = (MyEntityId) o;
		return Objects.equals(id, that.id) && Objects.equals(subId, that.subId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, subId);
	}
}
