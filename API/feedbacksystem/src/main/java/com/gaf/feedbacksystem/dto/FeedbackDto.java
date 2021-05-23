package com.gaf.feedbacksystem.dto;


import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private Integer feedbackID;
    private  String title;
    private AdminDto admin;
    private boolean isDeleted;
    private TypeFeedbackDto typeFeedback;
    private Collection<QuestionDto> questions;
}