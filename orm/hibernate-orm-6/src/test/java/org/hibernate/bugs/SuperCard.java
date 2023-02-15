package org.hibernate.bugs;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SuperCard<G extends SuperGeneration> {

    @Id
    private Long id;

    @ManyToOne
    private G generation;

    private double balance;
}
