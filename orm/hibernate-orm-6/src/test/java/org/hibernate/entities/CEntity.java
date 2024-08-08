package org.hibernate.entities;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "c_entities")
public class CEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "b_id")
    private Long bEntityId;

    @ManyToOne
    @JoinColumn(name = "b_id", updatable = false, insertable = false)
    private B1Entity b_entity;

}