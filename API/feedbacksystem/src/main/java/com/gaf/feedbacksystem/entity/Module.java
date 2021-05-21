package com.gaf.feedbacksystem.entity;


import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
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


    @Column(name="startTime")
    @Temporal(TemporalType.DATE)

    private Date startTime;
    @Column(name="endTime")
    @Temporal(TemporalType.DATE)
    private Date  endTime;

    private boolean isDeleted;

    @Column(name = "feedbackStartTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date  feedbackStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "feedbackEndTime")
    private Date  feedbackEndTime;

    @ManyToOne
    private Feedback feedback;

}
