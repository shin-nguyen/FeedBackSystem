package com.gaf.feedbacksystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;


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

    private Date startTime;
    private Date endTime;

    private boolean isDeleted;

    private Timestamp feedbackStartTime;
    private Timestamp feedbackEndTime;

    @ManyToOne
    private Feedback feedback;

}
