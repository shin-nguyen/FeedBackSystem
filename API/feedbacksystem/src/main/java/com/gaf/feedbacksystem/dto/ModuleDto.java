package com.gaf.feedbacksystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate startTime;
    private LocalDate endTime;
    private boolean isDeleted;
    private LocalDateTime feedbackStartTime;
    private LocalDateTime feedbackEndTime;
    private Feedback feedback;
}
