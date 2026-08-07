package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@Table(name = "questionnaire_questions")
public class QuestionnaireQuestion {

    public QuestionnaireQuestion() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "question", columnDefinition = "text", nullable = false)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owning_artifact_id")
    private QuestionnaireArtifact owningArtifact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionnaireArtifact getOwningArtifact() {
        return owningArtifact;
    }

    public void setOwningArtifact(QuestionnaireArtifact owningArtifact) {
        this.owningArtifact = owningArtifact;
    }
}