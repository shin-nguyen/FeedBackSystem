package com.gaf.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class implements Serializable {
    @SerializedName("classID")
    @Expose
    private Integer classID;

    @SerializedName("className")
    @Expose
    private String className;

    public Class(String className, String capacity, Date startTime, Date endTime) {
        this.className = className;
        this.capacity = capacity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @SerializedName("capacity")
    @Expose
    private String capacity;

    @SerializedName("startTime")
    @Expose
    private Date startTime;

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
