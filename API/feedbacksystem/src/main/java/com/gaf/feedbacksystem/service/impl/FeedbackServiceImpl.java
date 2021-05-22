package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.FeedbackDto;
import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.Feedback;
import com.gaf.feedbacksystem.repository.ClazzRepository;
import com.gaf.feedbacksystem.repository.FeedbackRepository;
import com.gaf.feedbacksystem.service.IFeedbackService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;


    @Override
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbacks = feedbackRepository.findAllByDeletedIsFalse();
        List<FeedbackDto> feedbackDtos = ObjectMapperUtils.mapAll(feedbacks,FeedbackDto.class);
        return feedbackDtos;
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepository.deleteByFeedbackId(id);
    }
}
