package org.hibernate.bugs.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CHILD")
@DiscriminatorValue("24")
public class EntityChildTwoSameTable extends EntityParent {

    @Column(name = "child_two_name")
    private String childTwoName;

    public String getChildTwoName() {
        return childTwoName;
    }

    public void setChildTwoName(String childTwoName) {
        this.childTwoName = childTwoName;
    }
}
