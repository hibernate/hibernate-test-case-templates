package org.hibernate.bugs.entities;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Where;

@DiscriminatorValue("4")
@Entity
public class SalesAccountTeamUser extends EntityTeamUser implements Serializable {
    public SalesAccountTeamUser(Long id, Long designationId, Long userId, Instant createdAt,
                                Instant updatedAt, SalesAccount account) {
        super(id, designationId, userId, createdAt, updatedAt);
        this.salesAccount = account;
    }

    public SalesAccountTeamUser() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    private SalesAccount salesAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    @JoinColumn(name = "entity_id", referencedColumnName = "parent_sales_account_id", insertable = false, updatable = false)
    private SalesAccount parentSalesAccount;

    public void setAccount(SalesAccount salesAccount) {
        this.salesAccount = salesAccount;
    }

    public SalesAccount getAccount() {
        return salesAccount;
    }
}
