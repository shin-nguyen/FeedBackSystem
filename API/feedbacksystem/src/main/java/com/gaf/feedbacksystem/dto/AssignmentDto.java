package com.gaf.feedbacksystem.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
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


    public TrainerDto getTrainer() {
        return getPrimaryKey().getTrainer();
    }
    @JsonSetter("trainer")
    public void setTrainer(TrainerDto trainer) {
        getPrimaryKey().setTrainer(trainer);
    }

    private String registrationCode="";

}

