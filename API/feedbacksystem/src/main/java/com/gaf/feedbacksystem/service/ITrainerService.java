package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.TrainerDto;

import java.util.List;


public interface ITrainerService {
    List<TrainerDto> findAll();
    void save(TrainerDto trainer);
    TrainerDto findByUserName(String userName);
}
