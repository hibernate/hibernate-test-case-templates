package org.hibernate.bugs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="ITEM")
public class Item {
	
    @Id
    @GeneratedValue(generator = "ItemSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ItemSeq", sequenceName = "ITEM_SEQ", allocationSize = 50)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Cart.class)
    @JoinColumn(name = "CART"
    		+ "_ID")
	private Cart cart;
	
	@Column(name = "ITEM_NO", length = 255)
	private String itemNo;

	/**
	 * INTERNAL, managed via {@link Cart#addItem(OfferItem)}
	 */
	public void setCartInternal(Cart offer) {
		this.cart = offer;
	}
	
	public Cart getCart() {
		return cart;
	}

	public Long getId() {
		return id;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

}
