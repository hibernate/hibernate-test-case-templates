package org.hibernate.bugs;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "questionnaire_answers")
public class QuestionnaireAnswer implements Serializable {

    public QuestionnaireAnswer() {
    }

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artifact_id", updatable = false)
    private QuestionnaireArtifact artifact;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "question_id")
    private QuestionnaireQuestion question;

    @Column(name = "answer")
    private String answer;
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public QuestionnaireArtifact getArtifact() {
        return artifact;
    }

    public void setArtifact(QuestionnaireArtifact artifact) {
        this.artifact = artifact;
    }

    public QuestionnaireQuestion getQuestion() {
        return question;
    }

    public void setQuestion(QuestionnaireQuestion question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}