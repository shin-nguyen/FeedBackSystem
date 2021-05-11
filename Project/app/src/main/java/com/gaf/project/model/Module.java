package com.gaf.project.model;

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
@Builder
public class Module {
    private Integer moduleID;
    private Admin admin;
    private String moduleName;
    private LocalDate startTime;
    private LocalDate endTime;
    private boolean isDeleted;
    private LocalDateTime feedbackStartTime;
    private LocalDateTime feedbackEndTime;
    private Feedback feedback;
}
