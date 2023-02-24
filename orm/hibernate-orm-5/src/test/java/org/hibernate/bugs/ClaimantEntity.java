package com.zurichna.claim.entity.test;

import com.zurichna.claim.entity.Injury;
import com.zurichna.claim.entity.claim.ClaimPartyRoleEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name="CLAIMANT")
public class ClaimantEntity {

    @EmbeddedId
    private ClaimantFolderPK id;

    public ClaimantFolderPK getId() {
        return id;
    }

    public void setId(ClaimantFolderPK id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "NAME")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyGroup("injuryEntities")
    @LazyToOne(LazyToOneOption.PROXY)
    @JoinColumns({
            @JoinColumn(name = "CLM_NBR", referencedColumnName = "CLM_NBR"),
            @JoinColumn(name = "DB_PARTN_NBR", referencedColumnName = "DB_PARTN_NBR"),
            @JoinColumn(name = "PROD_TRNG_CD", referencedColumnName = "PROD_TRNG_CD"),
            @JoinColumn(name = "CLM_SUB_NBR", referencedColumnName = "CLM_SUB_NBR") })
    @Valid
    private InjuryEntity injury;

    public InjuryEntity getInjury() {
        return injury;
    }

    public void setInjury(InjuryEntity injury) {
        this.injury = injury;
    }

    public PartyEntity getClmtPtyRole() {
        return clmtPtyRole;
    }

    public void setClmtPtyRole(PartyEntity clmtPtyRole) {
        this.clmtPtyRole = clmtPtyRole;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.PROXY)
    @JoinColumn(name = "CLM_PTY_ROLE_ID", referencedColumnName = "CLM_PTY_ROLE_ID")
    @Valid
    @NotFound(action= NotFoundAction.IGNORE)
    private PartyEntity clmtPtyRole;

}
