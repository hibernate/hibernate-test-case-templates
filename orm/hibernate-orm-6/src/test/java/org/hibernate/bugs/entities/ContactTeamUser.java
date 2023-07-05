package org.hibernate.bugs.entities;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Where;

@DiscriminatorValue("2")
//@Where(clause = "entity_type=2")
@Entity
public class ContactTeamUser extends EntityTeamUser implements Serializable {
    public ContactTeamUser(Long id, Long designationId, Long userId, Instant createdAt,
                           Instant updatedAt, SalesContact contact) {
        super(id, designationId, userId, createdAt, updatedAt);
        this.contact = contact;
    }

    public ContactTeamUser() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    private SalesContact contact;

    public void setContact(SalesContact salesContact) {
        this.contact = salesContact;
    }

    public SalesContact getContact() {
        return contact;
    }
}
