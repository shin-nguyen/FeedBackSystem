package com.gaf.feedbacksystem.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private Integer questionID;
    private TopicDto topic;
    private  String questionContent;
    private  boolean isDeleted;
}
