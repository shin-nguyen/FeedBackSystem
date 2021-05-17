package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AdminDto;
import com.gaf.feedbacksystem.dto.TraineeDto;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.repository.AdminRepository;
import com.gaf.feedbacksystem.repository.TraineeRepository;
import com.gaf.feedbacksystem.service.ITraineeService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements ITraineeService {

    @Autowired
    private TraineeRepository traineeRepository;

    @Override
    public List<TraineeDto> findAll() {
        return null;
    }

    @Override
    public void save(TraineeDto trainee) {

    }

    @Override
    public TraineeDto findByUserName(String userName) {
        Trainee trainee= traineeRepository.findByUserName(userName);
        TraineeDto traineeDto = ObjectMapperUtils.map(trainee,TraineeDto.class);
        return traineeDto;
    }

    @Override
    public void deleteById(String userName) {

    }


}
