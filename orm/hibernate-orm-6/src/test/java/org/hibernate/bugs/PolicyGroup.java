package org.hibernate.bugs;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ListIndexBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class PolicyGroup {

    @Id
    @SequenceGenerator(name = "POLICY_GROUP_SEQ", sequenceName = "policy_group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POLICY_GROUP_SEQ")
    private Long id;

    @Version
    private int saveVersion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "policy_group", foreignKey = @ForeignKey(name = "fk_policygrouprisk_group"))
    @OrderColumn(name = "sort_order")
    @ListIndexBase(1)
    private List<PolicyGroupRisk> risks;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "policy_group", foreignKey = @ForeignKey(name = "fk_policy_policygroup"))
    @OrderColumn(name = "policy_group_position")
    @ListIndexBase(1)
    private List<Policy> policies;

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
