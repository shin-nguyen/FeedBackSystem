package com.gaf.feedbacksystem.dto;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public  class AssignmentIdDto implements java.io.Serializable {
    private ClassDto mClass;
    private ModuleDto module;
    private TrainerDto trainer;
}
