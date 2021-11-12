package org.hibernate.bugs.hhh14910.model.xml;

import org.hibernate.bugs.hhh14910.model.IMultiTableEntity;

public class XMLEmbedMultiTableEnt implements IMultiTableEntity {
    private int id;
    private String name;
    private XMLMTEmbeddable embeddedObj;

    public XMLEmbedMultiTableEnt() {
        embeddedObj = new XMLMTEmbeddable();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public XMLMTEmbeddable getEmbeddedObj() {
        return embeddedObj;
    }

    public void setEmbeddedObj(XMLMTEmbeddable embeddedObj) {
        this.embeddedObj = embeddedObj;
    }

    @Override
    public String getCity() {
        return embeddedObj.getCity();
    }

    @Override
    public String getState() {
        return embeddedObj.getState();
    }

    @Override
    public String getStreet() {
        return embeddedObj.getStreet();
    }

    @Override
    public String getZip() {
        return embeddedObj.getZip();
    }

    @Override
    public void setCity(String city) {
        embeddedObj.setCity(city);
    }

    @Override
    public void setState(String state) {
        embeddedObj.setState(state);
    }

    @Override
    public void setStreet(String street) {
        embeddedObj.setStreet(street);
    }

    @Override
    public void setZip(String zip) {
        embeddedObj.setZip(zip);
    }
}
