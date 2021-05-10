package com.gaf.project.model;

import com.gaf.project.model.BaseUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainer extends BaseUser {
    private String phone;
    private String address;
    private boolean isActive;
    private Integer idSkill;
    private String activationCode;
    private String resetPasswordCode;
    private boolean isReceiveNotification;
}
