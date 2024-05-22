package org.hibernate.bugs.entityWithPrivateParameterizedProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class AlternativeAbsTwo<ONE extends AlternativeAbsOne<?>, THREE extends AlternativeAbsThree<?, ?>>
{
    @Id
    @GeneratedValue
    private Long id;

    private String absTwoStringProp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "three_id")
    private THREE three;

    @OneToMany(mappedBy = "two", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ONE> ones = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbsTwoStringProp() {
        return absTwoStringProp;
    }

    public void setAbsTwoStringProp(String absTwoStringProp) {
        this.absTwoStringProp = absTwoStringProp;
    }

    public THREE getThree() {
        return three;
    }

    public void setThree(THREE three) {
        this.three = three;
    }

    public Set<ONE> getOnes() {
        return ones;
    }

    public void setOnes(Set<ONE> ones) {
        this.ones = ones;
    }
}
