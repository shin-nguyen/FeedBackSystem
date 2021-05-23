package com.gaf.feedbacksystem.service.impl;

import java.util.List;

import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.entity.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaf.feedbacksystem.repository.AssignmentRepository;
import com.gaf.feedbacksystem.service.IAssignmentService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignmentServiceImpl implements IAssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public List<AssignmentDto> findAll() {
        List<Assignment> assignments = assignmentRepository.findAll();
        List<AssignmentDto> assignmentDtos = ObjectMapperUtils.mapAll(assignments,  AssignmentDto.class);
        return assignmentDtos;
    }

    @Override
    public AssignmentDto save(AssignmentDto assignmentDto) {
        Assignment assignment = ObjectMapperUtils.map(assignmentDto,Assignment.class);
        return ObjectMapperUtils.map(assignmentRepository.save(assignment),AssignmentDto.class);
    }

    @Override
    public List<AssignmentDto> findByTrainerUserName(String username) {
        List<Assignment> assignments = assignmentRepository.findByTrainerUserName(username);
        List<AssignmentDto> assignmentDtos = ObjectMapperUtils.mapAll(assignments, AssignmentDto.class);
        return assignmentDtos;

    }

    @Override
    @Transactional
    public AssignmentDto update(String userName,AssignmentDto newAssignment) {
        deleteById(newAssignment.getmClass().getClassID(),newAssignment.getModule().getModuleID(),userName);
        Assignment assignment = ObjectMapperUtils.map(newAssignment,Assignment.class);
        return ObjectMapperUtils.map(assignmentRepository.save(assignment),AssignmentDto.class);
    }

    @Override
    public void deleteById(Integer idClass, Integer idModule, String userName) {
        assignmentRepository.deleteByMClassAndModuleAndTrainer(idClass,idModule,userName);
    }

}
