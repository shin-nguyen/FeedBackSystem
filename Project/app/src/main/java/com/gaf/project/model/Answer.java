package com.gaf.project.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

public class Answer{

    @SerializedName("mClass")
    @Expose
    private Class mClass;
    @SerializedName("module")
    @Expose
    private Module module;
    @SerializedName("trainee")
    @Expose
    private Trainee trainee;

    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("value")
    @Expose
    private Integer value;
}

