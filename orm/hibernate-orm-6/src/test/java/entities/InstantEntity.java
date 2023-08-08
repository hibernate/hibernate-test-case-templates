package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@NamedQuery(name = "InstantEntity.findBetween", query = "SELECT i from InstantEntity i where i.dateValue between :from and :to")
@NamedQuery(name="InstantEntity.deleteAll", query="DELETE FROM InstantEntity i")
@Entity
@Table(name = "INSTANT_ENTITY")
public class InstantEntity {
    @Column(name = "DATE_VALUE")
    Instant dateValue;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    Instant created;

    @UpdateTimestamp
    Instant updated;

    @Version
    Long version;

    public InstantEntity() {}

    public Instant getDateValue() {
        return dateValue;
    }

    public void setDateValue(Instant dateValue) {
        this.dateValue = dateValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "InstantEntity{" +
                "dateValue=" + dateValue +
                ", id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", version=" + version +
                '}';
    }
}
