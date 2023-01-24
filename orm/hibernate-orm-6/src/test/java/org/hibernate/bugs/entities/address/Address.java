package org.hibernate.bugs.entities.address;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.bugs.entities.common.BaseEntity;
import org.hibernate.bugs.entities.user.User;
import org.hibernate.bugs.entities.user.UserId;

@Entity
@Table(name = "T_ADDRESS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Address extends BaseEntity<AddressId> {

    @EmbeddedId
    private final AddressId addressId;

    @Version
    private int version;

    protected Address() {
        this.addressId = null;
    }

    public Address(AddressId addressId) {
        this.addressId = addressId;
    }

    @Override
    public AddressId getId() {
        return this.addressId;
    }

    public abstract User getUser();

}
