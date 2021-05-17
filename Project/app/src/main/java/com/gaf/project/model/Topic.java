package com.gaf.project.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic{
    @SerializedName("topicID")
    private Integer topicID;
    @SerializedName("topicName")
    private String topicName;
}