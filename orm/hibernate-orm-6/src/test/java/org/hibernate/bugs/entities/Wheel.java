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
@Table(name="WHEEL")
public class Wheel {
	
    @Id
    @GeneratedValue(generator = "WheelSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "WheelSeq", sequenceName = "WHEEL_SEQ", allocationSize = 50)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Cart.class)
    @JoinColumn(name = "CART_ID")
	private Cart cart;
	
	@Column(name = "WHEEL_NO", length = 255)
	private String wheelNo;

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

	public String getWheelNo() {
		return wheelNo;
	}

	public void setWheelNo(String wheelNo) {
		this.wheelNo = wheelNo;
	}

}
