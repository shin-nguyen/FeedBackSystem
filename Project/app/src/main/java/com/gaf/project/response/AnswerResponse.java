package com.gaf.project.response;

import com.gaf.project.model.Answer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class AnswerResponse {
    @SerializedName("answers")
    @Expose
    private List<Answer> answers=null;
}
