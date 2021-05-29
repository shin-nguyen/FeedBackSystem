package com.gaf.project.response;

import com.gaf.project.model.Comment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class CommentResponse {
    @SerializedName("comments")
    @Expose
    private List<Comment> comments=null;

}
