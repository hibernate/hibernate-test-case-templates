package  org.hibernate.bugs;

import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="PARTY")
public class PartyEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CLM_PTY_ROLE_ID")
    private int clmPtyRoleId;

    @Column(name="IS_ACTIVE")
    private String isActive;

    public int getClmPtyRoleId() {
        return clmPtyRoleId;
    }

    public void setClmPtyRoleId(int clmPtyRoleId) {
        this.clmPtyRoleId = clmPtyRoleId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,  mappedBy = "clmtPtyRole",optional = false)
    @LazyGroup("claimantPartyEntities")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private ClaimantEntity claimant;
}
