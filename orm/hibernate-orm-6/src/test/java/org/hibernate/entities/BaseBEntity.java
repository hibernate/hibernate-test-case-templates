package org.hibernate.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Common customer items useful in all contexts where a customer is used.
 */
@MappedSuperclass
public class BaseBEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "b_entity")
    private Set<CEntity> c_entities = new LinkedHashSet<>();

}
