package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.TraineeAssignmentDto;
import com.gaf.feedbacksystem.entity.TraineeAssignment;

import java.util.List;

public interface ITraineeAssignmentService {
    TraineeAssignmentDto save(TraineeAssignmentDto traineeAssignmentDto);

    boolean checkCodeByTraineeId (String username, String code);
}
