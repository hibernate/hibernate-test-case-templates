package org.hibernate.bugs;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CLAIMANT")
public class InjuryEntity {

    @EmbeddedId
    private ClaimantFolderPK id;

    public ClaimantFolderPK getId() {
        return id;
    }

    public void setId(ClaimantFolderPK id) {
        this.id = id;
    }

    public String getInjuryName() {
        return injuryName;
    }

    public void setInjuryName(String injuryName) {
        this.injuryName = injuryName;
    }

    @Column(name = "INJ_NAME")
    private String injuryName;

}
