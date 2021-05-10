package com.gaf.project.model;

import lombok.*;



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
    private Date startTime;
    private Date endTime;
    private boolean isDeleted = false;

    private Collection<Trainee> trainees;
}
