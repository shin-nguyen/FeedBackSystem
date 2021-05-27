package com.gaf.feedbacksystem.service;


import com.gaf.feedbacksystem.dto.TraineeDto;

import java.util.List;

public interface ITraineeService {
    List<TraineeDto> findAll();
    void save(TraineeDto trainee);
    TraineeDto findByUserName(String userName);
    void deleteById(String userName);
}
