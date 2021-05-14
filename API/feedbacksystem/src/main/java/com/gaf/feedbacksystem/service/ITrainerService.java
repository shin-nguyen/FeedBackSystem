package com.gaf.feedbacksystem.service;


import com.gaf.feedbacksystem.dto.TrainerDto;
import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.entity.Trainer;

import java.util.List;
import java.util.Optional;

public interface ITrainerService {
    List<TrainerDto> findAll();
    void save(TrainerDto trainer);
    TrainerDto findByUserName(String userName);
}
