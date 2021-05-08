package com.gaf.feedbacksystem.dto;

import com.gaf.feedbacksystem.entity.Assignment;
import com.gaf.feedbacksystem.entity.Trainee;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data

public  class TraineeAssignmentIdDto {
    private AssignmentDto assignment;
    private TraineeDto trainee;
}