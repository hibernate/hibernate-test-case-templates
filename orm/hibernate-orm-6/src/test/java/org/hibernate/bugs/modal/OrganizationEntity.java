package org.hibernate.bugs.modal;

import jakarta.persistence.*;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "\"organization\"")
@SoftDelete
public class OrganizationEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private String displayPath;


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

    public String getDisplayPath() {
        return displayPath;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }


}