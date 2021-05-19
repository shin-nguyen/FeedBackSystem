package com.gaf.project.model;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    @SerializedName("moduleID")
    private Integer moduleID;
    @SerializedName("admin")
    private Admin admin;
    @SerializedName("moduleName")
    private String moduleName;
    @SerializedName("startTime")
    private Date startTime;
    @SerializedName("endTime")
    private Date endTime;
    @SerializedName("deleted")
    private boolean isDeleted;

    @SerializedName("feedbackStartTime")
    private Date feedbackStartTime;
    @SerializedName("feedbackEndTime")
    private Date feedbackEndTime;
    @SerializedName("feedback")
    private Feedback feedback;
}
