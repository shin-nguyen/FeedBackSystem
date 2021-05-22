package com.gaf.feedbacksystem.service;

import java.util.List;

import com.gaf.feedbacksystem.dto.FeedbackDto;

public interface IFeedbackService {
	
	List<FeedbackDto> findAll();
	
	FeedbackDto findById(Integer feedbackId);
	
	FeedbackDto update(FeedbackDto feedbackDto);
	
	FeedbackDto save(FeedbackDto feedbackDto);
	
	void deleteById(Integer id);


}
