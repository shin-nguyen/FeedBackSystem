package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.AnswerDto;
import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.dto.TraineeCommentDto;

import java.util.List;

public interface ICommentService {
    TraineeCommentDto save(TraineeCommentDto traineeCommentDto);
    List<TraineeCommentDto> findByMClassAndModule(Integer idClass, Integer idModule);
}
