package com.gaf.feedbacksystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TraineeCommentDto {
    TraineeCommentIdDto primaryKey = new TraineeCommentIdDto();

    private TraineeCommentIdDto getPrimaryKey(){
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

    private String comment;
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
