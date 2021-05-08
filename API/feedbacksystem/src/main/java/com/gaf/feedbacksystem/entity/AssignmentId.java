package com.gaf.feedbacksystem.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public  class AssignmentId implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classID")
    private Class mClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moduleID")
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainerId",referencedColumnName = "username")
    private Trainer trainer;

}
