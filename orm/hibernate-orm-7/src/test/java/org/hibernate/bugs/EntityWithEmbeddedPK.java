package org.hibernate.bugs;

import static jakarta.persistence.AccessType.PROPERTY;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Access;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class EntityWithEmbeddedPK {

	@EmbeddedId
	private PK id = new PK();

	@Embeddable
	@Access(PROPERTY)
	public static class PK implements Serializable {

		private Long id;

		public PK() {
			super();
		}

		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (obj.getClass() != this.getClass()) {
				return false;
			}
			return Objects.equals(((PK) obj).id, this.id);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(this.id);
		}
	}

}
