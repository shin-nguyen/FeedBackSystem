package com.gaf.feedbacksystem.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.gaf.feedbacksystem.entity.Class;
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
    public AssignmentDto update(AssignmentDto assignmentDto,AssignmentDto newAssignment) {
//        deleteById(new AssignmentIdDto());
//        Assignment assignment = ObjectMapperUtils.map(assignmentDto,Assignment.class);
//
//        return ObjectMapperUtils.map(assignmentRepository.save(assignment),AssignmentDto.class);
        return null;
    }

    @Override
    public void deleteById(Integer idClass, Integer idModule, String userName) {
        assignmentRepository.deleteByMClassAndModuleAndTrainer(idClass,idModule,userName);
    }

}
