package com.gaf.feedbacksystem.dto;

import com.gaf.feedbacksystem.entity.Feedback;
import com.gaf.feedbacksystem.entity.Topic;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private Integer questionID;
    private Topic topic;
    private  String questionContent;
    private  boolean isDeleted;
}
