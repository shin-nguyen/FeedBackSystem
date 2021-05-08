package com.gaf.feedbacksystem.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public  class TraineeCommentId implements java.io.Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classID")
    private Class mClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moduleID")
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traineeId",referencedColumnName = "username")
    private Trainee trainee;

}