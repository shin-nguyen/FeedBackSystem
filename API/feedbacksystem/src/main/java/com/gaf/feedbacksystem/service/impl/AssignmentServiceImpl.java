package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AssignmentDto;
import com.gaf.feedbacksystem.dto.AssignmentIdDto;
import com.gaf.feedbacksystem.dto.TrainerDto;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Assignment;

import com.gaf.feedbacksystem.entity.AssignmentId;
import com.gaf.feedbacksystem.repository.AssignmentRepository;
import com.gaf.feedbacksystem.service.IAssignmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements IAssignmentService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public List<AssignmentDto> findAll() {
        List<Assignment> assignments = assignmentRepository.findAll();
        List<AssignmentDto> assignmentDtos = mapper.map(assignments, (Type) AssignmentDto.class);
        return assignmentDtos;
    }

    @Override
    public void save(AssignmentDto assignmentDto) {
        Assignment assignment = mapper.map(assignmentDto,Assignment.class);
        assignmentRepository.save(assignment);
    }

    @Override
    public List<AssignmentDto> findByTrainerUserName(String username) {
        List<Assignment> assignments = assignmentRepository.findByTrainerUserName(username);
        List<AssignmentDto> assignmentDtos = mapper.map(assignments, (Type) AssignmentDto.class);
        return assignmentDtos;

    }

    @Override
    public void update(AssignmentDto Assignment) {

    }

    @Override
    public boolean delete(AssignmentIdDto assignmentIdDto) {
        AssignmentId assignmentId = mapper.map(assignmentIdDto,AssignmentId.class);
        try {
            assignmentRepository.deleteById(assignmentId);
        }
        catch (Exception e){
            return  false;
        }
        return true;
    }
}
