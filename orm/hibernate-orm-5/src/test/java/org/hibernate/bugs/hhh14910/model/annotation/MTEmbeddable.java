package org.hibernate.bugs.hhh14910.model.annotation;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class MTEmbeddable implements Serializable {
    private String street;
    private String city;
    private String state;
    private String zip;

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
}
