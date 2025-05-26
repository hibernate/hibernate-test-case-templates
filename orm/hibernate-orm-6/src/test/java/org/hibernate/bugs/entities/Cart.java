package org.hibernate.bugs.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="CART")
public class Cart {
	
    @Id
    @GeneratedValue(generator = "CartSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CartSeq", sequenceName = "CART_SEQ", allocationSize = 50)
    private Long id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", targetEntity = Item.class, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Item> items = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", targetEntity = Wheel.class, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Wheel> wheels = new ArrayList<>();
	
	@Column(name = "CART_NO", length = 255)
	private String cartNo;

	public List<Item> getItems() {
		return items;
	}
	
	public Item getItem(int index) {
		return items.get(index);
	}

	public void addItem(Item objectToAdd) {
        if (objectToAdd == null) {
            throw new NullPointerException("Can't add null to association Items of " + this);
        }
        if (items.contains(objectToAdd)) {
            return;
        }
        objectToAdd.setCartInternal(this);
        items.add(objectToAdd);
	}
	
    public void removeItem(Item objectToRemove) {
        if (objectToRemove == null) {
            return;
        }
        if (items.remove(objectToRemove)) {
            objectToRemove.setCartInternal(null);
        }
    }
    
	public void addWheel(Wheel objectToAdd) {
        if (objectToAdd == null) {
            throw new NullPointerException("Can't add null to association Wheels of " + this);
        }
        if (getWheels().contains(objectToAdd)) {
            return;
        }
        objectToAdd.setCartInternal(this);
        getWheels().add(objectToAdd);
	}
	
    public void removeWheel(Wheel objectToRemove) {
        if (objectToRemove == null) {
            return;
        }
        if (getWheels().remove(objectToRemove)) {
            objectToRemove.setCartInternal(null);
        }
    }

	public List<Wheel> getWheels() {
		return wheels;
	}
	
	public Wheel getWheel(int index) {
		return wheels.get(index);
	}

	public String getCartNo() {
		return cartNo;
	}

	public void setCartNo(String orderNo) {
		this.cartNo = orderNo;
	}

	public Long getId() {
		return id;
	}

}
