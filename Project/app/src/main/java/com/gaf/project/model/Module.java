package com.gaf.project.model;

import java.security.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Module {
    private Integer moduleID;
    private Admin admin;
    private String moduleName;
    private Date startTime;
    private Date endTime;
    private boolean isDeleted;
    private Timestamp feedbackStartTime;
    private Timestamp feedbackEndTime;
    private Feedback feedback;
}
