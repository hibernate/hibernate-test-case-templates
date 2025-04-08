package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("4")
public class UrlArtifact extends AbstractFileArtifact {

    public UrlArtifact() {
    }

    @Column(name = "url", nullable = false)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}