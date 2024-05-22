package org.hibernate.bugs.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import org.hibernate.bugs.entity.AbsTwo;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class AbsThree<TWO extends AbsTwo<?, ?>, FOUR>
{
    @Id
    @GeneratedValue
    private Long id;

    private String absThreeStringProp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "four_id")
    FOUR four;

    @OneToMany(mappedBy = "three", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<TWO> twos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbsThreeStringProp() {
        return absThreeStringProp;
    }

    public void setAbsThreeStringProp(String absThreeStringProp) {
        this.absThreeStringProp = absThreeStringProp;
    }

    public FOUR getFour() {
        return four;
    }

    public void setFour(FOUR four) {
        this.four = four;
    }

    public Set<TWO> getTwos() {
        return twos;
    }

    public void setTwos(Set<TWO> twos) {
        this.twos = twos;
    }
}
