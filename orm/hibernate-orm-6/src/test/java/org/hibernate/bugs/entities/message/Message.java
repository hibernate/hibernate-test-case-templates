package org.hibernate.bugs.entities.message;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.bugs.entities.address.Address;
import org.hibernate.bugs.entities.address.AddressId;
import org.hibernate.bugs.entities.common.BaseEntity;
import org.hibernate.bugs.entities.user.User;

@Entity
@Table(name = "T_MESSAGE")
public class Message extends BaseEntity<MessageId> {

    @EmbeddedId
    private final MessageId messageId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "SENDER_ADDRESS_ID")
    private final Address address;

    @Version
    private int version;

    protected Message() {
        this.messageId = null;
        this.address = null;
    }

    public Message(MessageId messageId,Address address) {
        this.messageId = messageId;
        this.address = address;
    }

    @Override
    public MessageId getId() {
        return this.messageId;
    }

    public Address getAddress() {
        return address;
    }

}
