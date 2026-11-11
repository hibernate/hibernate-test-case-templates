package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("1")
public class FileArtifact extends AbstractFileArtifact {
    public FileArtifact() {
    }
}