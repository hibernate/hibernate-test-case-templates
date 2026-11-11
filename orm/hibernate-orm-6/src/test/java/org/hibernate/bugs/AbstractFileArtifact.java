package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
public abstract class AbstractFileArtifact extends Artifact {
    public AbstractFileArtifact() {
    }

    @Column(name = "file_hash", nullable = false)
    protected String fileHash;

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}