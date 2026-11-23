package org.hibernate.bugs;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "int(2)")
@DiscriminatorValue("1")
public class Attestation implements Serializable {
    public Attestation() {
    }

    @Id
    protected UUID id = UUID.randomUUID();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "attestation_artifact_id", updatable = false)
    protected AttestationArtifact artifact;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AttestationArtifact getArtifact() {
        return artifact;
    }

    public void setArtifact(AttestationArtifact artifact) {
        this.artifact = artifact;
    }
}
