package com.gaf.feedbacksystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gaf.feedbacksystem.entity.Admin;
import com.gaf.feedbacksystem.entity.Feedback;

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
    private Admin admin;
    private String moduleName;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date startTime;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date endTime;

    private boolean isDeleted;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private Date feedbackStartTime;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private Date feedbackEndTime;
    private Feedback feedback;
}
