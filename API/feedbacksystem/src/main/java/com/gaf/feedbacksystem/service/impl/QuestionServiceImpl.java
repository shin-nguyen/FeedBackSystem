package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.QuestionDto;
import com.gaf.feedbacksystem.entity.Question;
import com.gaf.feedbacksystem.repository.QuestionRepository;
import com.gaf.feedbacksystem.service.IQuestionService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public List<QuestionDto> findAll() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = ObjectMapperUtils.mapAll(questions,QuestionDto.class);
        return questionDtos;
    }

    @Override
    public QuestionDto findById(Integer QuestionId) {
        return null;
    }

    @Override
    public QuestionDto update(QuestionDto questionDto) {
        return null;
    }

    @Override
    public QuestionDto save(QuestionDto questionDto) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
