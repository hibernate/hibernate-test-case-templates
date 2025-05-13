
package org.hibernate.bugs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CHILD")
@DiscriminatorValue("24")
public class EntityChildTwoSameDiscriminator extends EntityParent {

    @Column(name = "child_two_name")
    private String childTwoName;

    public String getChildTwoName() {
        return childTwoName;
    }

    public void setChildTwoName(String childTwoName) {
        this.childTwoName = childTwoName;
    }
}
