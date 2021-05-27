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

public class Enrollment implements Serializable {

    @SerializedName("mClass")
    @Expose
    private Class mClass;

    @SerializedName("trainee")
    @Expose
    private Trainee trainee;
}
