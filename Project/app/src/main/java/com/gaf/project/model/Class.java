package com.gaf.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.*;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Class implements Serializable {
    @SerializedName("classID")
    @Expose
    private Integer classID;

    @SerializedName("className")
    @Expose
    @ToString.Include
    private String className;

    public Class(String className, Integer capacity, Date startTime, Date endTime) {
        this.className = className;
        this.capacity = capacity;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Class(Integer classID,String className, Integer capacity, Date startTime, Date endTime) {
        this.classID = classID;
        this.className = className;
        this.capacity = capacity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @SerializedName("capacity")
    @Expose
    private Integer capacity;

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
    private List<Trainee> trainees = null;

    @Override
    public String toString() {
        return getClassName();
    }

    public Class(Integer classID, String className) {
        this.classID = classID;
        this.className = className;
    }
}
