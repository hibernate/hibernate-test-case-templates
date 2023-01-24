package org.hibernate.bugs.entities.user;


import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.bugs.entities.address.AddressA;

@Entity
@DiscriminatorValue("USER_A")
public class UserA extends User {

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "ADDRESS_ID")
    private AddressA addressA;

    protected UserA() {
    }

    public UserA(UserId userId) {
        super(userId);
    }

    public void setAddressA(AddressA addressA) {
        this.addressA = addressA;
    }

}
