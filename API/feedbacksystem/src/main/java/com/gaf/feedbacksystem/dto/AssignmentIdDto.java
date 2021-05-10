package com.gaf.feedbacksystem.dto;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public  class AssignmentIdDto implements java.io.Serializable {
    private ClassDto mClass;
    private ModuleDto module;
    private TrainerDto trainer;
}