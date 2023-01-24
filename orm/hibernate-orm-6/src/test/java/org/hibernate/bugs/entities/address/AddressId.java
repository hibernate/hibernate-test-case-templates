package org.hibernate.bugs.entities.address;

import static org.hibernate.bugs.entities.common.IdPrefix.ADDRESS;
import static org.hibernate.bugs.entities.common.IdPrefix.USER;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import org.hibernate.bugs.entities.common.SingleStringValueHolderId;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "ADDRESS_ID"))
public class AddressId extends SingleStringValueHolderId implements Serializable {

    protected AddressId() {
        super(ADDRESS);
    }

}
