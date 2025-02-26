package org.hibernate.bugs;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class MyEntityId {
	private long val;

	protected MyEntityId() {
	}

	public MyEntityId(long val) {
		this.val = val;
	}

	public long getVal() {
		return val;
	}

	public void setVal(long val) {
		this.val = val;
	}

	@Override
	public boolean equals(Object o) {
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		MyEntityId id = (MyEntityId) o;
		return val == id.val;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( val );
	}
}
