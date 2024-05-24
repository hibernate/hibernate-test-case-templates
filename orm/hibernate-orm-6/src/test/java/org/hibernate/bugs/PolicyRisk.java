package org.hibernate.bugs;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class PolicyRisk {

    @Id
    @SequenceGenerator(name = "POLICY_RISK_SEQ", sequenceName = "policy_risk__id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POLICY_RISK_SEQ")
    private Long id;

    @Version
    private int saveVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy", insertable = false, updatable = false, nullable = true)
    private Policy policy;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "policy_group_risk", foreignKey = @ForeignKey(name = "fk_policyrisk_grouprisk"))
    private PolicyGroupRisk policyGroupRisk;

    public PolicyRisk(Policy policy, PolicyGroupRisk policyGroupRisk) {
        this.policy = policy;
        this.policyGroupRisk = policyGroupRisk;
    }

    public PolicyRisk() {

    }

}
