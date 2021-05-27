package com.gaf.project.model;


import android.content.Intent;

import com.gaf.project.model.AnswerID;
import com.gaf.project.model.Module;
import com.gaf.project.model.Question;
import com.gaf.project.model.Trainee;

import java.util.List;

import kotlin.jvm.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

public class Answer{
    AnswerID primaryKey = new AnswerID();

    private AnswerID getPrimaryKey(){
        return primaryKey;
    }

    public Class getmClass() {
        return getPrimaryKey().getMClass();
    }
    public void setmClass(Class mClass) {
        getPrimaryKey().setMClass(mClass);
    }

    public Module getModule() {
        return getPrimaryKey().getModule();
    }
    public void setModule(Module module) {
        getPrimaryKey().setModule(module);
    }


    public Trainee getTrainee() {
        return getPrimaryKey().getTrainee();
    }
    public void setTrainee(Trainee trainee) {
        getPrimaryKey().setTrainee(trainee);
    }

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

