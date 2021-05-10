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
public class Feedback{
    private Integer feedbackID;
    private  String title;
    private Admin admin;
    private boolean isDeleted;
    private TypeFeedback typeFeedback;
    private Collection<Question> questions;
}