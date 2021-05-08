package com.gaf.feedbacksystem.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.class",
                joinColumns = @JoinColumn(name = "classID")),
        @AssociationOverride(name = "primaryKey.trainee",
                joinColumns = @JoinColumn(name = "traineeID",referencedColumnName = "username")),
        @AssociationOverride(name = "primaryKey.module",
                joinColumns = @JoinColumn(name = "moduleID")),
        @AssociationOverride(name = "primaryKey.question",
                joinColumns = @JoinColumn(name = "questionID")),
})
public class Answer{
    @EmbeddedId
    AnswerID primaryKey = new AnswerID();

    private  AnswerID getPrimaryKey(){
        return primaryKey;
    }
    @Transient
    public Class getmClass() {
        return getPrimaryKey().getMClass();
    }
    public void setmClass(Class mClass) {
        getPrimaryKey().setMClass(mClass);
    }

    @Transient
    public Module getModule() {
        return getPrimaryKey().getModule();
    }
    public void setModule(Module module) {
        getPrimaryKey().setModule(module);
    }

    @Transient
    public Trainee getTrainee() {
        return getPrimaryKey().getTrainee();
    }
    public void setTrainee(Trainee trainee) {
        getPrimaryKey().setTrainee(trainee);
    }

    @Transient
    public Question getQuestion() {
        return getPrimaryKey().getQuestion();
    }
    public void setQuestion(Question question) {
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

