package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Inheritance;
import org.hibernate.Hibernate;
import org.hibernate.type.YesNoConverter;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "REFERENCE_CODES")
@DiscriminatorColumn(name = "domain")
@Inheritance
@IdClass(ReferenceCode.Pk.class)
public abstract class ReferenceCode implements Serializable {

    public ReferenceCode() {
    }

    public ReferenceCode(String domain, String code) {
        this.domain = domain;
        this.code = code;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getCode() {
        return this.code;
    }

    public String toString() {
        return "ReferenceCode(domain=" + this.getDomain() + ", code=" + this.getCode() + ")";
    }

    public static class Pk implements Serializable {
        private String domain;
        private String code;

        public Pk(String domain, String code) {
            this.domain = domain;
            this.code = code;
        }

        public Pk() {
        }

        public String getDomain() {
            return this.domain;
        }

        public String getCode() {
            return this.code;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof Pk)) return false;
            final Pk other = (Pk) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$domain = this.getDomain();
            final Object other$domain = other.getDomain();
            if (this$domain == null ? other$domain != null : !this$domain.equals(other$domain)) return false;
            final Object this$code = this.getCode();
            final Object other$code = other.getCode();
            if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Pk;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $domain = this.getDomain();
            result = result * PRIME + ($domain == null ? 43 : $domain.hashCode());
            final Object $code = this.getCode();
            result = result * PRIME + ($code == null ? 43 : $code.hashCode());
            return result;
        }

        public String toString() {
            return "ReferenceCode.Pk(domain=" + this.getDomain() + ", code=" + this.getCode() + ")";
        }
    }

    @Id
    @Column(insertable = false, updatable = false)
    private String domain;

    @Id
    private String code;

    public static String getCodeOrNull(final ReferenceCode referenceCode) {
        return referenceCode != null ? referenceCode.getCode() : null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final ReferenceCode that = (ReferenceCode) o;

        if (!Objects.equals(getDomain(), that.getDomain())) return false;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getDomain());
        result = 31 * result + (Objects.hashCode(getCode()));
        return result;
    }
}
