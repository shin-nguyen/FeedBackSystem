package com.gaf.feedbacksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AnswerIdDto {
    private ClassDto mClass;
    private ModuleDto module;
    private TrainerDto trainee;
    private QuestionDto question;
}
