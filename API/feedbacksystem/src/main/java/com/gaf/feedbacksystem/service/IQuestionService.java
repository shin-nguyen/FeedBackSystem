package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.QuestionDto;

import java.util.List;

public interface IQuestionService {
    List<QuestionDto> findAll();
    List<QuestionDto> findByDeleteFalse();
    List<QuestionDto> findAllByTopicId(Integer id);
    QuestionDto findById(Integer QuestionId);
    QuestionDto update(QuestionDto questionDto);
    QuestionDto save(QuestionDto questionDto);
    void deleteById(Integer id);
}
