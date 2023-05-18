package org.hibernate;

import jakarta.persistence.*;

@Entity
public class EntityD {
    @Id
    @GeneratedValue(generator= "entityD", strategy = GenerationType.TABLE)
    @TableGenerator(name = "entityD", table="my_seq",pkColumnName = "my_seq_name", valueColumnName = "my_seq_val")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
