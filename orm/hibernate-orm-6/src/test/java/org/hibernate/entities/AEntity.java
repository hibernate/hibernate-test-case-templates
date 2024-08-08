package org.hibernate.entities;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "a_entities")
public class AEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "b_entity_id", updatable = false, insertable = false)
    private B2Entity b_entity;

    @Column(name = "b_entity_id")
    private Long b_entity_id;

}
