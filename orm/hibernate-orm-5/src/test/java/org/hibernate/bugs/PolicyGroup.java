package org.hibernate.bugs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class PolicyGroup {

    @Id
    @SequenceGenerator(name = "POLICY_GROUP_SEQ", sequenceName = "policy_group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POLICY_GROUP_SEQ")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "policy_group", foreignKey = @ForeignKey(name = "fk_policy_policygroup"))
    private List<Policy> policies;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "policy_group", foreignKey = @ForeignKey(name = "fk_policygrouprisk_group"))
    private List<PolicyGroupRisk> risks;

    public PolicyGroup() {
        this.policies = new ArrayList<>();
        this.risks = new ArrayList<>();
    }

    public void addGroupRisk(PolicyGroupRisk risk) {
        this.risks.add(risk);
    }

    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }
}
