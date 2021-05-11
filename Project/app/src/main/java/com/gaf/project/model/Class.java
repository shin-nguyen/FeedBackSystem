package com.gaf.project.model;

import lombok.*;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Class {

    private String classID;
    private String className;
    private String capacity;
    private LocalDate startTime;
    private LocalDate endTime;
    private boolean isDeleted = false;

    private Collection<Trainee> trainees;
}
