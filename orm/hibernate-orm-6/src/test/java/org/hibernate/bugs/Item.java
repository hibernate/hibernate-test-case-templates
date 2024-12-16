package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Item {

    @Id
    private long id;

    public Item(long id) {
        this.id = id;
    }
}