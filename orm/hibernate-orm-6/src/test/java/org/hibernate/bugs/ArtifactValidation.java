package org.hibernate.bugs;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artifact_validation")
public class ArtifactValidation implements Serializable {
    public ArtifactValidation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "validation_message", columnDefinition = "text")
    private String validationMessage;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "artifact_id", updatable = false)
    private Artifact artifact;

    @OneToMany(mappedBy = "artifactValidation", cascade = CascadeType.ALL)
    private Set<ControlValidation> detectedControls = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public Set<ControlValidation> getDetectedControls() {
        return detectedControls;
    }

    public void setDetectedControls(Set<ControlValidation> detectedControls) {
        this.detectedControls = detectedControls;
    }
}