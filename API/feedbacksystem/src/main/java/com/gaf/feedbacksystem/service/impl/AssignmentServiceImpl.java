package com.gaf.feedbacksystem.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.AssignmentIdDto;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.entity.Assignment;
import com.gaf.feedbacksystem.entity.AssignmentId;
import com.gaf.feedbacksystem.repository.AssignmentRepository;
import com.gaf.feedbacksystem.service.IAssignmentService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;

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
    public void save(AssignmentDto assignmentDto) {
        Assignment assignment = ObjectMapperUtils.map(assignmentDto,Assignment.class);
        assignmentRepository.save(assignment);
    }

    @Override
    public List<AssignmentDto> findByTrainerUserName(String username) {
        List<Assignment> assignments = assignmentRepository.findByTrainerUserName(username);
        List<AssignmentDto> assignmentDtos = ObjectMapperUtils.mapAll(assignments, AssignmentDto.class);
        return assignmentDtos;

    }

    @Override
    public void update(AssignmentDto Assignment) {

    }
    @Override
    public void deleteById(AssignmentIdDto assignmentIdDto) {
        AssignmentId assignmentId = ObjectMapperUtils.map(assignmentIdDto,AssignmentId.class);
        assignmentRepository.deleteById(assignmentId);
    }
}
