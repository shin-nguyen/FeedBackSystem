package com.gaf.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    @SerializedName("module")
    @Expose
    private Module module;
    @SerializedName("trainee")
    @Expose
    private Trainee trainee;
    @SerializedName("mClass")
    @Expose
    private Class mClass;
    @SerializedName("comment")
    @Expose
    private String comment;

}
