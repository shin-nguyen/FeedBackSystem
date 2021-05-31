package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.TraineeAssignmentDto;
import com.gaf.feedbacksystem.entity.TraineeAssignment;
import com.gaf.feedbacksystem.repository.TraineeAssignmentRepository;
import com.gaf.feedbacksystem.service.ITraineeAssignmentService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeAssignmentServiceImpl implements ITraineeAssignmentService {

    @Autowired
    private TraineeAssignmentRepository traineeAssignmentRepository;

    @Override
    public TraineeAssignmentDto save(TraineeAssignmentDto traineeAssignmentDto) {
        TraineeAssignment traineeAssignment = ObjectMapperUtils.map(traineeAssignmentDto, TraineeAssignment.class);
        return ObjectMapperUtils.map(traineeAssignmentRepository.save(traineeAssignment), TraineeAssignmentDto.class);
    }

    @Override
    public TraineeAssignmentDto checkIsAvailable(String username, String code) {
        return ObjectMapperUtils.map(traineeAssignmentRepository.compare(username, code), TraineeAssignmentDto.class);
    }

//    @Override
//    public void checkCodeIsAvailable(String username, String code) {
//        traineeAssignmentRepository.compare(username, code);
//    }
}
