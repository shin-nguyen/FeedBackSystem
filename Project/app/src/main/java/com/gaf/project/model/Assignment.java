package com.gaf.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Assignment{

    private static final long serialVersionUID = 1L;

    AssignmentId primaryKey = new AssignmentId();

    private AssignmentId getPrimaryKey(){
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


    public Trainer getTrainer() {
        return getPrimaryKey().getTrainer();
    }
    public void setTrainer(Trainer trainer) {
        getPrimaryKey().setTrainer(trainer);
    }


    private String registrationCode;

}

