package com.gaf.feedbacksystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Module {
    @Id
    private Integer moduleID;

    @ManyToOne
    @JoinColumn(name = "adminID",referencedColumnName = "username")
    private Admin admin;

    @Column(length = 50)
    private String moduleName;


    @Column(name="startTime",columnDefinition = "DATE")

    private LocalDate  startTime;
    @Column(name="endTime",columnDefinition = "DATE")
    private LocalDate  endTime;

    private boolean isDeleted;

    @Column(name = "feedbackStartTime")
    private LocalDateTime  feedbackStartTime;
    @Column(name = "feedbackEndTime")
    private LocalDateTime  feedbackEndTime;

    @ManyToOne
    private Feedback feedback;

}
