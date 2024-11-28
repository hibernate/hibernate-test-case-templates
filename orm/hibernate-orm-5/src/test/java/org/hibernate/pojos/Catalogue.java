package org.hibernate.pojos;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Catalogue {

	private String partNumber = "12345";
	private String userName = "testuser";

	/**
	 * Gets the part number.
	 *
	 * @return the part number
	 */
	@Id
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * Sets the part number.
	 * 
	 * @param partNumber the new part number
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

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

}
