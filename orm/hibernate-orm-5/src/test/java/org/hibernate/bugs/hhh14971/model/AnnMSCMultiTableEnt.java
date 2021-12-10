package org.hibernate.bugs.hhh14971.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

@Entity
@SecondaryTable(name = "SEC_TABLE2AMSC", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(table = "SEC_TABLE2AMSC")),
                      @AttributeOverride(name = "city", column = @Column(table = "SEC_TABLE2AMSC")),
                      @AttributeOverride(name = "state", column = @Column(table = "SEC_TABLE2AMSC")),
                      @AttributeOverride(name = "zip", column = @Column(table = "SEC_TABLE2AMSC")) })
public class AnnMSCMultiTableEnt extends AnnMSCMultiTable {
    @Id
    private int id;

    private String name;

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

    @Override
    public String toString() {
        return "AnnMSCMultiTableEnt [id=" + id + "]";
    }
}
