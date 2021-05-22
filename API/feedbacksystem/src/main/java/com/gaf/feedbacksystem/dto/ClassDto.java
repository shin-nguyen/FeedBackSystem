package com.gaf.feedbacksystem.dto;

import java.time.LocalDate;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Integer classID;
    private String className;
    private Integer capacity;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate startTime;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate endTime;
    private boolean isDeleted = false;
    private Collection<TraineeDto> trainees =null;
}
