package com.gaf.project.model;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("feedbackID")
    private Integer feedbackID;
    @SerializedName("title")
    private  String title;
    @SerializedName("admin")
    private Admin admin;
    @SerializedName("deleted")
    private boolean isDeleted;
    @SerializedName("typeFeedback")
    private TypeFeedback typeFeedback;
    @SerializedName("questions")
    private Collection<Question> questions;
}