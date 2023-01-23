package org.hibernate.bugs.domain.questionnaire;

import org.hibernate.bugs.domain.Study;

import java.util.ArrayList;

public class QuestionnaireBuilder {
  public static Builder create(Study study){
    return new Builder(study);
  }
  public static class Builder {
    private final ScreeningQuestionnaire screeningQuestionnaire;
    Builder(Study study) {
      this.screeningQuestionnaire=new ScreeningQuestionnaire(study,new ArrayList<>());
    }

    public ScreeningQuestionBuilder addQuestion(String text){
      QuestionBuilder.Builder questionBuilder = QuestionBuilder.create(text, this.screeningQuestionnaire);
      return new ScreeningQuestionBuilder(questionBuilder, this);
    }

    public ScreeningQuestionnaire build() {
      return this.screeningQuestionnaire;
    }

    public static class ScreeningQuestionBuilder{
      private final QuestionBuilder.Builder questionBuilder;
      private final Builder builder;

      public ScreeningQuestionBuilder(QuestionBuilder.Builder questionBuilder, Builder builder) {
        this.questionBuilder = questionBuilder;
        this.builder=builder;
      }

      public ScreeningQuestionBuilder addOptions(String ...text){
        questionBuilder.addOptions(text);
        return this;
      }

      public Builder buildQuestion(){
        StudyScreeningQuestion studyScreeningQuestion = questionBuilder.build();
        this.builder.screeningQuestionnaire.getQuestions().add(studyScreeningQuestion);
        return this.builder;
      }
    }
  }

}
