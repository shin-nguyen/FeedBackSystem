package com.gaf.feedbacksystem.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleDto {
    private Integer moduleID;
    private AdminDto admin;
    private String moduleName;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date startTime;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date endTime;

    private boolean isDeleted;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date feedbackStartTime;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date feedbackEndTime;
    private FeedbackDto feedback;
}
