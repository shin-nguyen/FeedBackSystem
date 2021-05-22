package com.gaf.feedbacksystem.service;

import java.util.List;

import com.gaf.feedbacksystem.dto.FeedbackDto;

public interface IFeedbackService {
	
	List<FeedbackDto> findAll();
	
	FeedbackDto findById(Integer feedbackId);
	
	FeedbackDto update(FeedbackDto feedbackDto);
	
	FeedbackDto save(FeedbackDto feedbackDto);
	
	void deleteById(Integer id);

//    List<ClassDto> findAllByTrainer(String userName);
//    List<ClassDto> findAllByTrainee(String userName);
//    ClassDto findById(Integer classId);
//    ClassDto update(ClassDto classDto);
//    ClassDto save(ClassDto classDto);

}
