package org.hibernate.bugs.entities.user;


import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.bugs.entities.address.AddressB;

@Entity
@DiscriminatorValue("USER_B")
public class UserB extends User {

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "ADDRESS_ID")
    private AddressB addressB;

    protected UserB() {
    }

    public UserB(UserId userId) {
        super(userId);
    }

    public void setAddressB(AddressB addressB) {
        this.addressB = addressB;
    }

}
