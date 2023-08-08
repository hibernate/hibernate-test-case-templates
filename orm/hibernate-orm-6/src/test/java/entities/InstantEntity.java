package entities;

import jakarta.persistence.*;

import java.time.Instant;

@NamedQuery(name = "InstantEntity.findBetween", query = "SELECT i from InstantEntity i where i.dateValue between :from and :to")
@NamedQuery(name="InstantEntity.deleteAll", query="DELETE FROM InstantEntity i")
@Entity
@Table(name = "INSTANT_ENTITY")
public class InstantEntity {
    @Column(name = "DATE_VALUE", columnDefinition = "timestamp")
    Instant dateValue;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public InstantEntity() {}

    public void setDateValue(Instant dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public String toString() {
        return "InstantEntity{" +
                "dateValue=" + dateValue +
                ", id=" + id +
                '}';
    }
}
