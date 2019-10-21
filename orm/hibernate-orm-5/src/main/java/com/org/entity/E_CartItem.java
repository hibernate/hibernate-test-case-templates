package com.org.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Access(AccessType.FIELD)
@SequenceGenerator(name = "cartItemSeq", sequenceName = "cart_item_seq",
        initialValue = 1000)
public class E_CartItem {
    /**
     * Primary key, auto-generated
     */
    @Id
    @GeneratedValue(generator = "cartItemSeq",
            strategy = GenerationType.SEQUENCE)
    private long id;


    @ManyToOne
    private E_Cart cart;


    private String version;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public E_Cart getCart() {
        return cart;
    }

    public void setCart(E_Cart cart) {
        this.cart = cart;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
