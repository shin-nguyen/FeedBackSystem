package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.FeedbackDto;

import java.util.List;

public interface IFeedbackService {
	
	List<FeedbackDto> findAll();
	
	FeedbackDto findById(Integer feedbackId);
	
	FeedbackDto update(FeedbackDto feedbackDto);
	
	FeedbackDto save(FeedbackDto feedbackDto);
	
	void deleteById(Integer id);


}
