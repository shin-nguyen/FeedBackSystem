package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.FeedbackDto;
import com.gaf.feedbacksystem.entity.Feedback;
import com.gaf.feedbacksystem.service.IFeedbackService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaf.feedbacksystem.repository.ClazzRepository;
import com.gaf.feedbacksystem.repository.FeedbackRepository;

import java.util.List;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
	
    @Autowired
    FeedbackRepository feedbackRepository;


    @Override
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbacks = feedbackRepository.findAllByDeletedIsFalse();
        return ObjectMapperUtils.mapAll(feedbacks, FeedbackDto.class);
    }

    @Override
    public FeedbackDto findById(Integer feedbackId) {
        Feedback mFeedback = feedbackRepository.findByFeedbackID(feedbackId);
        return ObjectMapperUtils.map(mFeedback, FeedbackDto.class);
    }

    @Override
    public FeedbackDto update(FeedbackDto feedbackDto) {
        Feedback oldFeedback = feedbackRepository.findByFeedbackID(feedbackDto.getFeedbackID());

        oldFeedback.setTitle(feedbackDto.getTitle());

        return ObjectMapperUtils.map(feedbackRepository.save(oldFeedback), FeedbackDto.class);
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback mFeedback = ObjectMapperUtils.map(feedbackDto, Feedback.class);

        return ObjectMapperUtils.map(feedbackRepository.save(mFeedback), FeedbackDto.class);
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepository.deleteByFeedbackId(id);
    }
}
