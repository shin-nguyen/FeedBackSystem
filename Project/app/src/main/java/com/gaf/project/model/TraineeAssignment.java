package com.gaf.project.model;

import com.gaf.project.model.Assignment;
import com.gaf.project.model.Trainee;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kotlin.jvm.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
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


