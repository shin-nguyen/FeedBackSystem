package com.gaf.project.model;


import com.gaf.project.model.Assignment;
import com.gaf.project.model.Trainee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data

public  class TraineeAssignmentID implements Serializable {

    private Assignment assignment;

    private Trainee trainee;
}