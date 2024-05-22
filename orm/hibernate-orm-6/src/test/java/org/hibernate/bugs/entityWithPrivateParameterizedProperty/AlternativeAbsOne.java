package org.hibernate.bugs.entityWithPrivateParameterizedProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AlternativeAbsOne<TWO extends AlternativeAbsTwo<?, ?>>
{
    @Id
    @GeneratedValue
    private Long id;

    private String absOneStringProp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "two_id")
    private TWO two;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbsOneStringProp() {
        return absOneStringProp;
    }

    public void setAbsOneStringProp(String absOneStringProp) {
        this.absOneStringProp = absOneStringProp;
    }

    public TWO getTwo() {
        return two;
    }

    public void setTwo(TWO two) {
        this.two = two;
    }
}
