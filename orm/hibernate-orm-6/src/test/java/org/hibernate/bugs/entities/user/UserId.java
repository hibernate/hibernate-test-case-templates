package org.hibernate.bugs.entities.user;

import static org.hibernate.bugs.entities.common.IdPrefix.USER;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import org.hibernate.bugs.entities.common.SingleStringValueHolderId;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "USER_ID"))
public class UserId extends SingleStringValueHolderId implements Serializable {

    protected UserId() {
        super(USER);
    }

}
