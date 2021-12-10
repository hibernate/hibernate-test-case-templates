package org.hibernate.bugs.hhh14971.model;

public interface IMultiTableEntity {
    public String getCity();

    public void setCity(String city);

    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public String getState();

    public void setState(String state);

    public String getStreet();

    public void setStreet(String street);

    public String getZip();

    public void setZip(String zip);
}
