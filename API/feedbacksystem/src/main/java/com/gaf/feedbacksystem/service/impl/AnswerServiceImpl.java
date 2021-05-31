package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AnswerDto;
import com.gaf.feedbacksystem.entity.Answer;
import com.gaf.feedbacksystem.repository.AnswerRepository;
import com.gaf.feedbacksystem.service.IAnswerService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements IAnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public List<AnswerDto> findByMClassAndModule(Integer idClass, Integer idModule) {
        List<Answer> answers = answerRepository.findByMClassAndModule(idClass, idModule);
        List<AnswerDto> answerDtos = ObjectMapperUtils.mapAll(answers, AnswerDto.class);
        return answerDtos;
    }

    @Override
    public List<AnswerDto> findByMClassAndModuleAndQuestion(Integer idClass, Integer idModule, Integer idQuestion) {
        List<Answer> answers = answerRepository.findByMClassAndModuleAndQuestion(idClass, idModule, idQuestion);
        List<AnswerDto> answerDtos = ObjectMapperUtils.mapAll(answers, AnswerDto.class);
        return answerDtos;
    }

}

