package org.hibernate.bugs;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "control_validation")
public class ControlValidation implements Serializable {
    public ControlValidation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "control_domain_id")
    ControlDomain controlDomain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "control_id")
    Control control = null;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, name = "artifact_validation_id")
    ArtifactValidation artifactValidation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ControlDomain getControlDomain() {
        return controlDomain;
    }

    public void setControlDomain(ControlDomain controlDomain) {
        this.controlDomain = controlDomain;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public ArtifactValidation getArtifactValidation() {
        return artifactValidation;
    }

    public void setArtifactValidation(ArtifactValidation artifactValidation) {
        this.artifactValidation = artifactValidation;
    }
}