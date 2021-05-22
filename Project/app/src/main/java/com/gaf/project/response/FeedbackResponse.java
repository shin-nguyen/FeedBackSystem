package com.gaf.project.response;

import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class FeedbackResponse {
    @SerializedName("feedbacks")
    @Expose
    private List<Feedback> feedbacks=null;
}