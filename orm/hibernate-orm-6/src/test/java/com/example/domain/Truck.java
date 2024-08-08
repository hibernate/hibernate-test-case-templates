package com.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "TRUCK")
public class Truck extends Vehicle {

    public Truck(Owner owner) {
        super(owner);
    }

    public Truck() {
    }
}
