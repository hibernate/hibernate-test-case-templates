package org.hibernate.bugs;

import java.util.LinkedHashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "level1_loadAll",
                attributeNodes = {
                        @NamedAttributeNode(value = "childs", subgraph = "subgraph.childs")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "subgraph.childs",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "childs")
                                }
                        )
                }
        )
})
public class Level1 {

    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("id")
    private Set<Level2> childs = new LinkedHashSet<>();

    public Level1() {
    }

    public Level1(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Level2> getChilds() {
        return childs;
    }

    public void setChilds(Set<Level2> childs) {
        this.childs = childs;
    }

}
