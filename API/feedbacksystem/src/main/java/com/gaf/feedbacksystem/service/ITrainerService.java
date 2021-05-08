package com.gaf.feedbacksystem.service;


import com.gaf.feedbacksystem.dto.TrainerDto;
import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.entity.Trainer;

import java.util.List;
import java.util.Optional;

public interface ITrainerService {
    Optional<List<TrainerDto>> findAll();
    void save(TrainerDto trainer);
    Optional<TrainerDto> findByUserName(String userName);
}
