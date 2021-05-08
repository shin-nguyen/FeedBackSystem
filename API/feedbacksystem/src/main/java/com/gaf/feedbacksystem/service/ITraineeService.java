package com.gaf.feedbacksystem.service;


import com.gaf.feedbacksystem.entity.Trainee;

import java.util.List;
import java.util.Optional;

public interface ITraineeService {
    public List<Trainee> findAll();
    public void save(Trainee trainee);
    Optional<Trainee> findByUserName(String userName);
}
