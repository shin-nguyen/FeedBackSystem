package com.gaf.feedbacksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraineeAssignmentDto {



    TraineeAssignmentIdDto primaryKey = new TraineeAssignmentIdDto();

    private  TraineeAssignmentIdDto getPrimaryKey(){
        return primaryKey;
    }

    @Transient
    public AssignmentDto getAssignment() {
        return getPrimaryKey().getAssignment();
    }
    public void setAssignment(AssignmentDto assignment) {
        getPrimaryKey().setAssignment(assignment);
    }

    @Transient
    public TraineeDto getTrainee() {
        return getPrimaryKey().getTrainee();
    }
    public void setTrainee(TraineeDto trainee) {
        getPrimaryKey().setTrainee(trainee);
    }


}