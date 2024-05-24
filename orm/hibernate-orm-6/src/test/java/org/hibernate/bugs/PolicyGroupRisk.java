package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class PolicyGroupRisk {

    @Id
    @SequenceGenerator(name = "POLICY_GROUP_RISK_SEQ", sequenceName = "policy_group_risk__id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POLICY_GROUP_RISK_SEQ")
    private Long id;

    @Version
    private int saveVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_group", insertable = false, updatable = false, nullable = true)
    private PolicyGroup group;

    public PolicyGroupRisk(PolicyGroup group) {
        this.group = group;
    }

    public PolicyGroupRisk() {

    }

}
