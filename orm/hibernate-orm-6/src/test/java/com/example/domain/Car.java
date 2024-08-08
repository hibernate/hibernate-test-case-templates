package com.example.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CAR")
public class Car extends Vehicle {

    public Car(Owner owner) {
        super(owner);
    }

    public Car() {
    }
}
