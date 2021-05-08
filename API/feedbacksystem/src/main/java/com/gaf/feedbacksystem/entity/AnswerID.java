package com.gaf.feedbacksystem.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Embeddable
public  class AnswerID implements java.io.Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classID")
    private Class mClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moduleID")
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traineeID",referencedColumnName = "username")
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionID")
    private  Question question;
}