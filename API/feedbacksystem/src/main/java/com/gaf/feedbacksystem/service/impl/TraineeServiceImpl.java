package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.repository.AdminRepository;
import com.gaf.feedbacksystem.repository.TraineeRepository;
import com.gaf.feedbacksystem.service.ITraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements ITraineeService {

    @Autowired
    private TraineeRepository traineeRepository;

    @Override
    public List<Trainee> findAll() {
        return null;
    }

    @Override
    public void save(Trainee trainee) {

    }

    @Override
    public Optional<Trainee> findByUserName(String userName) {
        return Optional.empty();
    }


}
