package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "EntityA", uniqueConstraints = @UniqueConstraint(columnNames = {"columnAEntityA", "columnBEntityA"}))
public class EntityA extends BaseEntity {
    @Column(name = "columnAEntityA")
    public Long columnAEntityA;
    @Column(name = "columnBEntityA")
    public Long columnBEntityA;

    @OneToOne(mappedBy = "entityA")
    public EntityB entityB;
}
