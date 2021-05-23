package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.AssignmentDto;

import java.util.List;

public interface IAssignmentService {
    List<AssignmentDto> findAll();
    AssignmentDto save(AssignmentDto assignment);
    List<AssignmentDto> findByTrainerUserName(String username);
    AssignmentDto update(String userName,AssignmentDto newAssignment);
    void deleteById(Integer idClass,Integer idModule,String userName);
}
