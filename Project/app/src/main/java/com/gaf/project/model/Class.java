package com.gaf.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.*;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @SerializedName("classID")
    @Expose
    private String classID;

    @SerializedName("className")
    @Expose
    private String className;

    @SerializedName("capacity")
    @Expose
    private String capacity;

//    @SerializedName("startTime")
//    @Expose
//    private LocalDate startTime;

    @SerializedName("endTime")
    @Expose
    private Date endTime;

    @SerializedName("deleted")
    @Expose
    private boolean isDeleted;

    @SerializedName("trainees")
    @Expose
    private Collection<Trainee> trainees = null;
}
