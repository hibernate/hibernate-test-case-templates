package org.hibernate.bugs;

import java.util.LinkedHashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Level2 {

    @Id
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", nullable = false)
    private Level1 parent;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("id")
    private Set<Level3> childs = new LinkedHashSet<>();


    public Level2() {
    }

    public Level2(Level1 parent, Long id) {
        this.parent = parent;
        this.id = id;
        parent.getChilds().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Level1 getParent() {
        return parent;
    }

    public void setParent(Level1 parent) {
        this.parent = parent;
    }

    public Set<Level3> getChilds() {
        return childs;
    }

    public void setChilds(Set<Level3> childs) {
        this.childs = childs;
    }
}
