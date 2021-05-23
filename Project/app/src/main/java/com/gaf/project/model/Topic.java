package com.gaf.project.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic implements Serializable {
    @SerializedName("topicID")
    private Integer topicID;
    @SerializedName("topicName")
    private String topicName;
    @Override
    public String toString() {
        return getTopicName();
    }
}