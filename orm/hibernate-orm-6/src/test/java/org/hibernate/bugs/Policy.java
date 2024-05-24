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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class Policy {

    @Id
    @SequenceGenerator(name = "POLICY_SEQ", sequenceName = "policy_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POLICY_SEQ")
    private Long id;

    @Version
    private int saveVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_group", insertable = false, updatable = false, nullable = true)
    private PolicyGroup policyGroup;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "policy", foreignKey = @ForeignKey(name = "fk_policyrisk_policy"))
    @OrderColumn(name = "sort_order")
    @ListIndexBase(1)
    private List<PolicyRisk> risks = new ArrayList<>();

    public Policy(PolicyGroup policyGroup) {
        this.policyGroup = policyGroup;
    }

    public Policy() {

    }

    public void addRisk(PolicyRisk risk) {
        this.risks.add(risk);
    }

}
