package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.AnswerDto;

import java.util.List;

public interface IAnswerService {
    List<AnswerDto> addAll(List<AnswerDto> answerDtos);
    List<AnswerDto> findByMClassAndModule(Integer idClass,Integer idModule);
    List<AnswerDto> findByMClassAndModuleAndQuestion(Integer idClass,Integer idModule, Integer idQuestion);
   }