package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AdminDto;
import com.gaf.feedbacksystem.dto.TrainerDto;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.entity.Trainer;
import com.gaf.feedbacksystem.repository.TraineeRepository;
import com.gaf.feedbacksystem.repository.TrainerRepository;
import com.gaf.feedbacksystem.service.ITraineeService;
import com.gaf.feedbacksystem.service.ITrainerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements ITrainerService {
    @Autowired
    TrainerRepository trainerRepository ;
    @Autowired
    private ModelMapper mapper;



    @Override
    public Optional<List<TrainerDto>> findAll() {
        return Optional.empty();
    }

    @Override
    public void save(TrainerDto trainer) {

    }

    @Override
    public Optional<TrainerDto> findByUserName(String userName) {
        Optional<Trainer> trainer= trainerRepository.findByUserName(userName);
        Optional<TrainerDto> adminDto = mapper.map(trainer, (Type) TrainerDto.class);
        return adminDto;
    }
}
