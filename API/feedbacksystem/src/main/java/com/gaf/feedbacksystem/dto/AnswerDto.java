package com.gaf.feedbacksystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {
    AnswerIdDto primaryKey = new AnswerIdDto();

    private  AnswerIdDto getPrimaryKey(){
        return primaryKey;
    }

    public ClassDto getmClass() {
        return getPrimaryKey().getMClass();
    }
    public void setmClass(ClassDto mClass) {
        getPrimaryKey().setMClass(mClass);
    }


    public ModuleDto getModule() {
        return getPrimaryKey().getModule();
    }
    public void setModule(ModuleDto module) {
        getPrimaryKey().setModule(module);
    }


    public TrainerDto getTrainee() {
        return getPrimaryKey().getTrainee();
    }
    public void setTrainee(TrainerDto trainee) {
        getPrimaryKey().setTrainee(trainee);
    }


    public QuestionDto getQuestion() {
        return getPrimaryKey().getQuestion();
    }
    public void setQuestion(QuestionDto question) {
        getPrimaryKey().setQuestion(question);
    }


    private Integer value;
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}
