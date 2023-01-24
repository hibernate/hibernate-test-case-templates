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
import org.hibernate.bugs.entities.user.UserB;
import org.hibernate.bugs.entities.user.UserId;

@Entity
@DiscriminatorValue("ADDRESS_B")
public class AddressB extends Address {

    @OneToOne(mappedBy = "addressB", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private UserB userB;

    protected AddressB() {
    }

    public AddressB(AddressId addressId) {
        super(addressId);
    }

    public void setUserB(UserB userB) {
        this.userB = userB;
        userB.setAddressB(this);
    }

    @Override
    public User getUser() {
        return this.userB;
    }

}
