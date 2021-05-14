package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.AssignmentIdDto;
import com.gaf.feedbacksystem.dto.TrainerDto;

import java.util.List;
import java.util.Optional;

public interface IAssignmentService {
    List<AssignmentDto> findAll();
    void save(AssignmentDto assignment);
    List<AssignmentDto> findByTrainerUserName(String username);
    void update(AssignmentDto Assignment);
    void deleteById(AssignmentIdDto assignmentIdDto);
}
