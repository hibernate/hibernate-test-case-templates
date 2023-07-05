package org.hibernate.bugs.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_accounts")
public class SalesAccount
        implements Serializable {
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_sales_account_id")
    private Long parentSalesAccountId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "parent_owner_id")
    private Long parentOwnerId;

    @OneToMany(mappedBy = "salesAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SalesAccountTeamUser> teamUsers;

    @OneToMany(mappedBy = "parentSalesAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SalesAccountTeamUser> parentAccountTeamUsers;

    @OneToMany(mappedBy = "primaryAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SalesContact> contacts;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
