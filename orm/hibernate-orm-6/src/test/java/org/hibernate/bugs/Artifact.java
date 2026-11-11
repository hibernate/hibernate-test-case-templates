package org.hibernate.bugs;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "artifact_type", discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "artifact")
public abstract class Artifact implements Serializable {

    public Artifact() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    protected Long id;

    @Column(name = "description")
    protected String description;

    @OneToOne(mappedBy = "artifact", cascade = CascadeType.ALL)
    ArtifactValidation validation = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArtifactValidation getValidation() {
        return validation;
    }

    public void setValidation(ArtifactValidation validation) {
        this.validation = validation;
    }
}