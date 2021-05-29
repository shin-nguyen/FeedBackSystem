package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.TraineeDto;

import java.util.List;

public interface IClassService {
	List<ClassDto> findAll();
	List<ClassDto> findAllByTrainer(String userName);
	List<ClassDto> findAllByTrainee(String userName);
	ClassDto findById(Integer classId);
	ClassDto update(ClassDto classDto);
	ClassDto save(ClassDto classDto);
	void deleteById(Integer id);

	ClassDto updateTrainee(Integer oldIdClass, Integer newIdClass, TraineeDto traineeDto);

	ClassDto deleteTrainee(String id,ClassDto classDto);
}
