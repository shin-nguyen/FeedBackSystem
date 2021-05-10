package com.gaf.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assignment {
    private Integer id;
    private Integer moduleID;
    private Integer classID;
    private Integer trainerID;
    private String registration_code;
}
