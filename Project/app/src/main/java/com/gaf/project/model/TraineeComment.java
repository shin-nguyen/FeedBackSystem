package com.gaf.project.model;

import com.gaf.project.model.Trainee;

import kotlin.jvm.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class TraineeComment{

   TraineeCommentId primaryKey = new TraineeCommentId();

    TraineeCommentId getPrimaryKey(){
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


    public Trainee getTraine() {
        return getPrimaryKey().getTrainee();
    }
    public void setTrainee(Trainee trainee) {
        getPrimaryKey().setTrainee(trainee);
    }


    private String comment;
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

