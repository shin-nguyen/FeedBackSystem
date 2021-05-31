package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.TraineeAssignmentDto;
import com.gaf.feedbacksystem.entity.TraineeAssignment;

import java.util.List;

public interface ITraineeAssignmentService {
    TraineeAssignmentDto save(TraineeAssignmentDto traineeAssignmentDto);

    TraineeAssignmentDto checkIsAvailable(String username, String code);
//    void checkCodeIsAvailable(String username, String code);
}
