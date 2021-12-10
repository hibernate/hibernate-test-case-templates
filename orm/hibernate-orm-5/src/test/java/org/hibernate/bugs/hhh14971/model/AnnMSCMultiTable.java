package org.hibernate.bugs.hhh14971.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import org.hibernate.bugs.hhh14971.model.IMultiTableEntity;

/**
 * Mapped Superclass defining fields which will be overriden to a secondary field by the inheriting entity.
 */
@MappedSuperclass
// @SecondaryTable(name="SEC_TABLE2MSC", pkJoinColumns=@PrimaryKeyJoinColumn(name="id"))
public abstract class AnnMSCMultiTable implements IMultiTableEntity {

    // Fields on the secondary database
    // @Column(table="SEC_TABLE2MSC")
    private String street;

    // @Column(table="SEC_TABLE2MSC")
    private String city;

    // @Column(table="SEC_TABLE2MSC")
    private String state;

    // @Column(table="SEC_TABLE2MSC")
    private String zip;

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String getZip() {
        return zip;
    }

    @Override
    public void setZip(String zip) {
        this.zip = zip;
    }

}
