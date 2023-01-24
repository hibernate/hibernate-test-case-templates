package org.hibernate.bugs.entities.message;

import static org.hibernate.bugs.entities.common.IdPrefix.OUTBOX_MESSAGE;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import org.hibernate.bugs.entities.common.SingleStringValueHolderId;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "MESSAGE_ID"))
public class MessageId extends SingleStringValueHolderId implements Serializable {

    protected MessageId() {
        super(OUTBOX_MESSAGE);
    }

}
