package com.gaf.feedbacksystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainer extends BaseUserEntity{
    @Column(length = 50)
    private String phone;
    private String address;
    private boolean isActive;
    private Integer idSkill;
    @Column(length = 50)
    private String activationCode;
    @Column(length = 50)
    private String resetPasswordCode;
    private boolean isReceiveNotification;
}