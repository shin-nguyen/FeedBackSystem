package com.gaf.feedbacksystem.dto;

import lombok.Data;

@Data
public  class TraineeCommentIdDto {
    private ClassDto mClass;
    private ModuleDto module;
    private TrainerDto trainee;
}