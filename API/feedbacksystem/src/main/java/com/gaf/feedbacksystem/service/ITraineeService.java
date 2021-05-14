package com.gaf.feedbacksystem.service;


import com.gaf.feedbacksystem.dto.TraineeDto;
import com.gaf.feedbacksystem.entity.Trainee;

import java.util.List;
import java.util.Optional;

public interface ITraineeService {
    List<TraineeDto> findAll();
    void save(TraineeDto trainee);
    TraineeDto findByUserName(String userName);
    void deleteById(String userName);
}
