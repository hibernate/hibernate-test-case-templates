package org.hibernate.bugs;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Set;

@Entity
@DiscriminatorValue("2")
public class MissingArtifactAttestation extends Attestation {

    @ManyToOne
    @JoinColumn(name = "referenced_artifact_id")
    private Artifact referencedArtifact;
}
