package org.hibernate.bugs;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("2")
public class QuestionnaireArtifact extends Artifact {

    public QuestionnaireArtifact() {
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "artifact")
    @OrderColumn(name = "question_order", nullable = false)
    private List<QuestionnaireAnswer> answers = new ArrayList<>();

    public List<QuestionnaireAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionnaireAnswer> answers) {
        this.answers = answers;
    }
}