package com.gaf.feedbacksystem.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public  class TraineeAssignmentID implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrationCode")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traineeId",referencedColumnName = "username")
    private  Trainee trainee;
}