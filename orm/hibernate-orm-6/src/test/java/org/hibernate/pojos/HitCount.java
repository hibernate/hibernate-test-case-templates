package org.hibernate.pojos;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class HitCount {

	private String id = "123456789";
	// private String partNumber = "12345";
	private String userName = "testUser";

	private Catalogue catalogue = null;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the part number.
	 *
	 * @return the part number
	 */
	// @Column(insertable = false, updatable = false)
	// public String getPartNumber() {
	// return partNumber;
	// }

	/**
	 * Sets the part number.
	 * 
	 * @param partNumber the new part number
	 */
	// public void setPartNumber(String partNumber) {
	// this.partNumber = partNumber;
	// }

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the catalogue.
	 *
	 * @return the catalogue
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partNumber")
	@NotFound(action = NotFoundAction.IGNORE)
	public Catalogue getCatalogue() {
		return catalogue;
	}

	/**
	 * Sets the catalogue
	 *
	 * @param catalogue the new catalogue
	 */
	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}

}
