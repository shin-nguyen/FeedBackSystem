package com.gaf.project.response;

import com.gaf.project.model.Topic;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class TopicResponse {
    @SerializedName("topics")
    @Expose
    private List<Topic> topic=null;
}
