package org.hibernate.bugs;


import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SuperGeneration {

    @Id
    private Long id;

    @ManyToOne
    private CardType type;
}
