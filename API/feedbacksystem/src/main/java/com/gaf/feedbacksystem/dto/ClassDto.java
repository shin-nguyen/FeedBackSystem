package com.gaf.feedbacksystem.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassDto {
    private Integer classID;
    private String className;
    private Integer capacity;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date startTime;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date endTime;
    private boolean isDeleted = false;
    private Collection<TraineeDto> trainees;
}
