package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "EntityB", uniqueConstraints = @UniqueConstraint(columnNames = {"columnAEntityB", "columnBEntityB"}))
public class EntityB extends BaseEntity {
    @Column(name = "columnAEntityB")
    public Long columnAEntityB;
    @Column(name = "columnBEntityB")
    public Long columnBEntityB;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "columnAEntityB", referencedColumnName = "columnAEntityA", insertable = false, updatable = false),
            @JoinColumn(name = "columnBEntityB", referencedColumnName = "columnBEntityA", insertable = false, updatable = false)
    })
    public EntityA entityA;
}
