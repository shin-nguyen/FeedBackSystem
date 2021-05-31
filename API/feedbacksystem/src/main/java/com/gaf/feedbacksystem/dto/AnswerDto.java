package com.gaf.feedbacksystem.dto;


import com.fasterxml.jackson.annotation.JsonSetter;
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
    @JsonSetter("mClass")
    public void setmClass(ClassDto mClass) {
        getPrimaryKey().setMClass(mClass);
    }


    public ModuleDto getModule() {
        return getPrimaryKey().getModule();
    }
    @JsonSetter("module")
    public void setModule(ModuleDto module) {
        getPrimaryKey().setModule(module);
    }


    public TrainerDto getTrainee() {
        return getPrimaryKey().getTrainee();
    }
    @JsonSetter("trainee")
    public void setTrainee(TrainerDto trainee) {
        getPrimaryKey().setTrainee(trainee);
    }


    public QuestionDto getQuestion() {
        return getPrimaryKey().getQuestion();
    }
    @JsonSetter("question")
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