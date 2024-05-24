package org.hibernate.bugs;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class PolicyGroupRisk {

    @Id
    @SequenceGenerator(name = "POLICY_GROUP_RISK_SEQ", sequenceName = "policy_group_risk__id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POLICY_GROUP_RISK_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_group", insertable = false, updatable = false, nullable = true)
    private PolicyGroup group;

    public PolicyGroupRisk(PolicyGroup group) {
        this.group = group;
    }

    public PolicyGroupRisk() {

    }

}
