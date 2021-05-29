package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.TraineeAssignmentDto;

import java.util.List;

public interface ITraineeAssignmentService {
    TraineeAssignmentDto save(TraineeAssignmentDto traineeAssignmentDto);

//    void checkCodeIsAvailable(String username, String code);
}
