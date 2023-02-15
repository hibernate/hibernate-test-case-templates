package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CardType {

    @Id
    private String code;

    @ManyToOne(optional = false)
    private Client client;
}
