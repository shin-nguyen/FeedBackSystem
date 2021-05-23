package com.gaf.project.response;

import com.gaf.project.model.Trainer;
import com.gaf.project.model.TypeFeedback;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class TypeFeedbackResponse {
    @SerializedName("typeFeedbacks")
    @Expose
    private List<TypeFeedback> typeFeedbacks=null;
}
