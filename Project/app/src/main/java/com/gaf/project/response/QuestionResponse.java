package com.gaf.project.response;

import com.gaf.project.model.Assignment;
import com.gaf.project.model.Question;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class QuestionResponse {
    @SerializedName("questions")
    @Expose
    private List<Question> questions=null;
}
