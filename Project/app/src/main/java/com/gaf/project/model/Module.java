package com.gaf.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module implements  Serializable{
    @SerializedName("moduleID")
    private Integer moduleID;
    @SerializedName("admin")
    private Admin admin;
    @SerializedName("moduleName")
    @ToString.Include
    private String moduleName;
    @SerializedName("startTime")
    private Date startTime;
    @SerializedName("endTime")
    private Date endTime;
    @SerializedName("deleted")
    private boolean isDeleted;


    @SerializedName("feedbackStartTime")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date feedbackStartTime;

    public Module(Admin admin, String moduleName, Date startTime, Date endTime, Date feedbackStartTime, Date feedbackEndTime, Feedback feedback) {
        this.admin = admin;
        this.moduleName = moduleName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.feedbackStartTime = feedbackStartTime;
        this.feedbackEndTime = feedbackEndTime;
        this.feedback = feedback;
    }

    @SerializedName("feedbackEndTime")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date feedbackEndTime;

    public Module(Integer moduleID, Admin admin, String moduleName, Date startTime, Date endTime, Date feedbackStartTime, Date feedbackEndTime, Feedback feedback) {
        this.moduleID = moduleID;
        this.admin = admin;
        this.moduleName = moduleName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.feedbackStartTime = feedbackStartTime;
        this.feedbackEndTime = feedbackEndTime;
        this.feedback = feedback;
    }

    @SerializedName("feedback")
    private Feedback feedback;

    @Override
    public String toString() {
        return getModuleName();
    }

    public Module(Integer moduleID, String moduleName) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
    }
}
