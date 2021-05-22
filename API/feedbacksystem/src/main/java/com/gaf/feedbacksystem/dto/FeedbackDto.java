package com.gaf.feedbacksystem.dto;

import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Question;
import com.gaf.feedbacksystem.entity.TypeFeedback;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private Integer feedbackID;
    private  String title;
    private AdminDto admin;
    private boolean isDeleted = false;
    private TypeFeedbackDto typeFeedback;
    private Collection<QuestionDto> questions = null;
}