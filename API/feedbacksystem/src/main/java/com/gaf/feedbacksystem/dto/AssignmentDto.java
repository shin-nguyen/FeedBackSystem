package com.gaf.feedbacksystem.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentDto {
    AssignmentIdDto primaryKey = new AssignmentIdDto();

    private AssignmentIdDto getPrimaryKey(){
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


    public TrainerDto getTrainer() {
        return getPrimaryKey().getTrainer();
    }
    public void setTrainer(TrainerDto trainer) {
        getPrimaryKey().setTrainer(trainer);
    }

    private String registrationCode;

}

