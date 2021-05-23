package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.TypeFeedbackDto;

import java.util.List;

public interface ITypeFeedback {
    List<TypeFeedbackDto> findAll();
}
