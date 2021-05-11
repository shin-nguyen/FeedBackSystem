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

@Table(name = "Trainee_Assignment")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.trainee",
                joinColumns = @JoinColumn(name = "traineeID",referencedColumnName = "username")),
        @AssociationOverride(name = "primaryKey.assignment",
                joinColumns = @JoinColumn(name = "registrationCode",referencedColumnName = "registrationCode")),
})
public class TraineeAssignment{
    @EmbeddedId
    TraineeAssignmentID primaryKey = new TraineeAssignmentID();

    private  TraineeAssignmentID getPrimaryKey(){
        return primaryKey;
    }

    @Transient
    public Assignment getAssignment() {
        return getPrimaryKey().getAssignment();
    }
    public void setAssignment(Assignment assignment) {
        getPrimaryKey().setAssignment(assignment);
    }

    @Transient
    public Trainee getTraine() {
        return getPrimaryKey().getTrainee();
    }
    public void setTrainee(Trainee trainee) {
        getPrimaryKey().setTrainee(trainee);
    }


}

