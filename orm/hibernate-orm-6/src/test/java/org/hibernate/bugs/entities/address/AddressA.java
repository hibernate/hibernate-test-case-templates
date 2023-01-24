package org.hibernate.bugs.entities.address;


import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.bugs.entities.user.User;
import org.hibernate.bugs.entities.user.UserA;
import org.hibernate.bugs.entities.user.UserId;

@Entity
@DiscriminatorValue("ADDRESS_A")
public class AddressA extends Address {

    protected AddressA() {
    }

    public AddressA(AddressId addressId) {
        super(addressId);
    }

    @Override
    public User getUser() {
        return this.userA;
    }

    @OneToOne(mappedBy = "addressA", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private UserA userA;

    public void setUserA(UserA userA) {
        this.userA = userA;
        userA.setAddressA(this);
    }

}
