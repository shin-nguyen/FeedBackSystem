package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AnswerDto;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.entity.Answer;
import com.gaf.feedbacksystem.entity.Class;
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
    public List<AnswerDto> addAll(List<AnswerDto> answerDtos) {
        List<Answer> answers = ObjectMapperUtils.mapAll(answerDtos,Answer.class);

        return ObjectMapperUtils.mapAll(answerRepository.saveAll(answers), AnswerDto.class);
    }

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

