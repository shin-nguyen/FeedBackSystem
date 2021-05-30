package com.gaf.project.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements Serializable {
    @SerializedName("feedbackID")
    private Integer feedbackID;
    @SerializedName("title")
    private  String title;


    public Feedback(String title, TypeFeedback typeFeedback, List<Question> questions) {
        this.title = title;
        this.typeFeedback = typeFeedback;
        this.questions = questions;
    }


    @SerializedName("admin")
    private Admin admin;
    @SerializedName("deleted")
    private boolean isDeleted;
    @SerializedName("typeFeedback")
    private TypeFeedback typeFeedback;
    @SerializedName("questions")
    private Collection<Question> questions;

    public Feedback(Integer idFb, String title, TypeFeedback typeFeedback, List<Question> questionList) {
        this.feedbackID = idFb;
        this.title = title;
        this.typeFeedback = typeFeedback;
        this.questions = questionList;
    }

    @Override
    public String toString() {
        return getTitle();
    }


}