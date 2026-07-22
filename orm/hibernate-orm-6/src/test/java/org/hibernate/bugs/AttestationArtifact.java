package org.hibernate.bugs;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("5")
public class AttestationArtifact extends Artifact {

    public AttestationArtifact() {
    }

    @OneToMany(mappedBy = "artifact", cascade = CascadeType.ALL)
    private Set<Attestation> attestations = new HashSet<>();

    public Set<Attestation> getAttestations() {
        return attestations;
    }

    public void setAttestations(Set<Attestation> attestations) {
        this.attestations = attestations;
    }
}