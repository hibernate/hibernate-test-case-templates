package org.hibernate.bugs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

  @Column(name = "address_line1")
  private String addressLine1;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip")
  private String zip;

  public Address() {
  }

  public Address(String addressLine1, String city, String state, String zip) {
    this.addressLine1 = addressLine1;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }
}
