package com.gaf.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TraineeAssignment{

    @SerializedName("assignment")
    @Expose
    private Assignment assignment;

    @SerializedName("trainee")
    @Expose
    private Trainee trainee;
}


