package org.hibernate.bugs.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.bugs.entity.Three;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Four
{
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "four", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Three> threes = new HashSet<>();

    private String fourConcreteProp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Three> getThrees() {
        return threes;
    }

    public void setThrees(Set<Three> threes) {
        this.threes = threes;
    }

    public String getFourConcreteProp() {
        return fourConcreteProp;
    }

    public void setFourConcreteProp(String fourConcreteProp) {
        this.fourConcreteProp = fourConcreteProp;
    }
}
