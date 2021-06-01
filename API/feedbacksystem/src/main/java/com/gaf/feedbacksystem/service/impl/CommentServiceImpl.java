package com.gaf.feedbacksystem.service.impl;

import com.gaf.feedbacksystem.dto.AnswerDto;
import com.gaf.feedbacksystem.dto.ClassDto;
import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.dto.TraineeCommentDto;
import com.gaf.feedbacksystem.entity.Answer;
import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.TraineeComment;
import com.gaf.feedbacksystem.repository.CommentRepository;
import com.gaf.feedbacksystem.service.ICommentService;
import com.gaf.feedbacksystem.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public TraineeCommentDto save(TraineeCommentDto traineeCommentDto) {
        TraineeComment comment = ObjectMapperUtils.map(traineeCommentDto, TraineeComment.class);
        return ObjectMapperUtils.map(commentRepository.save(comment), TraineeCommentDto.class);
    }

    @Override
    public List<TraineeCommentDto> findByMClassAndModule(Integer idClass, Integer idModule) {
        List<TraineeComment> traineeComments = commentRepository.findByMClassAndModule(idClass, idModule);
        List<TraineeCommentDto> traineeCommentDtos = ObjectMapperUtils.mapAll(traineeComments, TraineeCommentDto.class);
        return traineeCommentDtos;
    }
}
