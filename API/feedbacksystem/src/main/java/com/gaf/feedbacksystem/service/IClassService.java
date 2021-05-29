package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.ClassDto;

import java.util.List;

public interface IClassService {
	List<ClassDto> findAll();
	List<ClassDto> findAllByTrainer(String userName);
	List<ClassDto> findAllByTrainee(String userName);
	ClassDto findById(Integer classId);
	ClassDto update(ClassDto classDto);
	ClassDto save(ClassDto classDto);
	void deleteById(Integer id);

	ClassDto updateTrainee(ClassDto classDto,Integer oldIdTrainee, Integer newIdTrainee);

	ClassDto deleteTrainee(String id,ClassDto classDto);
}
