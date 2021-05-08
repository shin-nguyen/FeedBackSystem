package com.gaf.feedbacksystem.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeFeedback {
    @Id
    private Integer typeID;

    @Column(length = 50)
    private String typeName;

    private  boolean isDeleted;
}
