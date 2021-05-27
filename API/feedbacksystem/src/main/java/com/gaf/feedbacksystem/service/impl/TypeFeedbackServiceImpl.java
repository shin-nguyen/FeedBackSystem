package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.TypeFeedbackDto;
import com.gaf.feedbacksystem.entity.TypeFeedback;
import com.gaf.feedbacksystem.repository.TypeFeedbackRepository;
import com.gaf.feedbacksystem.service.ITypeFeedback;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeFeedbackServiceImpl implements ITypeFeedback {
    @Autowired
    TypeFeedbackRepository typeFeedbackRepository;

    @Override
    public List<TypeFeedbackDto> findAll() {
        List<TypeFeedback> typeFeedbacks = typeFeedbackRepository.findAll();
        List<TypeFeedbackDto> typeFeedbackDtos = ObjectMapperUtils.mapAll(typeFeedbacks,TypeFeedbackDto.class);
        return typeFeedbackDtos;
    }
}
