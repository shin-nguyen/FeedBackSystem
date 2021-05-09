package com.gaf.project.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question{
    private Integer questionID;
    private Topic topic;
    private  String questionContent;
    private  boolean isDeleted;
    private Collection<Feedback> feedbacks;
}
