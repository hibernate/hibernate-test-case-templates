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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Policy {

    @Id
    @SequenceGenerator(name = "POLICY_SEQ", sequenceName = "policy_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POLICY_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_group", insertable = false, updatable = false, nullable = true)
    private PolicyGroup policyGroup;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "policy", foreignKey = @ForeignKey(name = "fk_policyrisk_policy"))
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
