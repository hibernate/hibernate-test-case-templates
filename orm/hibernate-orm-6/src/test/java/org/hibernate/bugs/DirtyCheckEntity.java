package org.hibernate.bugs;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity(name = "DirtyCheckEntity")
@Table(name = "DIRTY_ENTITY")
@Access(AccessType.PROPERTY)
@DynamicUpdate
@SequenceGenerator(
        name = "DIRTY_ENTITY_SEQ_GENERATOR",
        sequenceName = "DIRTY_ENTITY_SEQ",
        allocationSize = 50
)
public class DirtyCheckEntity {

    private Long id;
    private Long version;
    private String code;

    public DirtyCheckEntity() {
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEST_ENTITY_SEQ_GENERATOR")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (isChanged(this.id, id)) {
            this.id = id;
        }
    }

    @Version
    @Column(name = "VERSION")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        if (isChanged(this.version, version)) {
            this.version = version;
        }
    }

    @Basic
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (isChanged(this.code, code)) {
            this.code = code;
        }
    }

    protected static boolean isChanged(Object currentValue, Object newValue) {
        if (currentValue != null) {
            return !Objects.equals(currentValue, newValue);
        }
        return newValue != null;
    }
}
