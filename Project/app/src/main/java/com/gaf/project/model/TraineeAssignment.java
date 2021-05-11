package com.gaf.project.model;

import com.gaf.project.model.Assignment;
import com.gaf.project.model.Trainee;

import kotlin.jvm.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data

@AllArgsConstructor
@NoArgsConstructor

public class TraineeAssignment{

    TraineeAssignmentID primaryKey = new  TraineeAssignmentID();

    private TraineeAssignmentID getPrimaryKey(){
        return primaryKey;
    }

    public Assignment getAssignment() {
        return getPrimaryKey().getAssignment();
    }
    public void setAssignment(Assignment assignment) {
        getPrimaryKey().setAssignment(assignment);
    }

    public Trainee getTraine() {
        return getPrimaryKey().getTrainee();
    }
    public void setTrainee(Trainee trainee) {
        getPrimaryKey().setTrainee(trainee);
    }


}


