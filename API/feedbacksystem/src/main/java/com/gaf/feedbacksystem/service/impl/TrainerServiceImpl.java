package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AdminDto;
import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.dto.TrainerDto;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Module;
import com.gaf.feedbacksystem.entity.Trainee;
import com.gaf.feedbacksystem.entity.Trainer;
import com.gaf.feedbacksystem.repository.TraineeRepository;
import com.gaf.feedbacksystem.repository.TrainerRepository;
import com.gaf.feedbacksystem.service.ITraineeService;
import com.gaf.feedbacksystem.service.ITrainerService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
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

    @Override
    public List<TrainerDto> findAll() {
        List<Trainer> trainers = trainerRepository.findAll();
        List<TrainerDto> trainerDtos = ObjectMapperUtils.mapAll(trainers,TrainerDto.class);

        return trainerDtos;
    }

    @Override
    public void save(TrainerDto trainer) {

    }

    @Override
    public TrainerDto findByUserName(String userName) {
        Trainer trainer= trainerRepository.findByUserName(userName);
        TrainerDto trainerDto = ObjectMapperUtils.map(trainer,TrainerDto.class);
        return trainerDto;
    }
}
