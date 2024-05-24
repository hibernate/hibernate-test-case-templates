package org.hibernate.bugs;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class PolicyRisk {

    @Id
    @SequenceGenerator(name = "POLICY_RISK_SEQ", sequenceName = "policy_risk__id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POLICY_RISK_SEQ")
    private Long id;

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
