package com.gaf.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question implements Serializable {
    @SerializedName("questionID")
    private Integer questionID;
    @SerializedName("topic")
    private Topic topic;
    @SerializedName("questionContent")
    private  String questionContent;
    @SerializedName("deleted")
    private  boolean isDeleted;

    public Question(Topic topic, String questionContent) {
        this.topic = topic;
        this.questionContent = questionContent;
    }
}
