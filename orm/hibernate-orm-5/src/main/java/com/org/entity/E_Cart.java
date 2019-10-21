package com.org.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.HashMap;
import java.util.Map;

@Entity
@Access(AccessType.FIELD)
@SequenceGenerator(name = "cartSeq", sequenceName = "cart_seq",
        initialValue = 1000)
public class E_Cart {

    /**
     * Primary key, auto-generated
     */
    @Id
    @GeneratedValue(generator = "cartSeq",
            strategy = GenerationType.SEQUENCE)
    private long id;


    @OneToMany(mappedBy = "cart")
    @MapKey(name = "version")
    private Map<String, E_CartItem> cartItemMap = new HashMap<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, E_CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<String, E_CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }
}
