package com.gaf.project.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Feedback{
    @NonNull
    private Integer feedbackID;
    @NonNull
    private  String title;
    @NonNull
    private Admin admin;
    private boolean isDeleted;
    private TypeFeedback typeFeedback;
    private Collection<Question> questions;
}