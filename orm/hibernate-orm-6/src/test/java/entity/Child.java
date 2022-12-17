package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(name = "children")
@Where(clause = "deleted_at IS NULL")
public class Child
{
    @Id
    private Long id;

    @JoinColumn(name = "parent_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Parent parent;

    @Column(name = "deleted_at")
    private Date deletedAt;
}
