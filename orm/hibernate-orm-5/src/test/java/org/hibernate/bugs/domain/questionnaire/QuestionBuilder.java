package org.hibernate.bugs.domain.questionnaire;

import java.util.ArrayList;
import java.util.stream.Stream;

public class QuestionBuilder {

  public static Builder create(String text, ScreeningQuestionnaire qnaire){
    ScreeningQuestion screeningQuestion=new ScreeningQuestion(text);
    screeningQuestion.setOptions(new ArrayList<>());
    StudyScreeningQuestion studyScreeningQuestion = new StudyScreeningQuestion(screeningQuestion);
    studyScreeningQuestion.setQuestionnaire(qnaire);
    studyScreeningQuestion.setOrderNum(qnaire.getQuestions().size());
    return new Builder(studyScreeningQuestion);
  }

  public static class Builder {
    private final StudyScreeningQuestion studyScreeningQuestion;

    public Builder(StudyScreeningQuestion screeningQuestion) {
      this.studyScreeningQuestion = screeningQuestion;
    }

    private void addOption(String text){
      ScreeningQuestionOption option = new ScreeningQuestionOption(text, studyScreeningQuestion.getQuestion());
      option.setOrderNum(studyScreeningQuestion.getQuestion().getOptions().size());
      studyScreeningQuestion.getQuestion().getOptions().add(option);
    }

    public Builder addOptions(String ...text){
      Stream.of(text).forEach(this::addOption);
      return this;
    }

    public StudyScreeningQuestion build(){
      return this.studyScreeningQuestion;
    }
  }
}
