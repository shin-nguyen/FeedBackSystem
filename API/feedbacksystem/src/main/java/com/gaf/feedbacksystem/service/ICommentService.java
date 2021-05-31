package com.gaf.feedbacksystem.service;

import com.gaf.feedbacksystem.dto.ModuleDto;
import com.gaf.feedbacksystem.dto.TraineeCommentDto;

public interface ICommentService {
    TraineeCommentDto save(TraineeCommentDto traineeCommentDto);
}
