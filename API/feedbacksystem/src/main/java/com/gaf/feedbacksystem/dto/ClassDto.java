package com.gaf.feedbacksystem.dto;

import java.time.LocalDate;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ClassDto {
    private String classID;
    private String className;
    private String capacity;
    private LocalDate startTime;
    private LocalDate endTime;
    private boolean isDeleted = false;
    private Collection<TraineeDto> trainees;
}
