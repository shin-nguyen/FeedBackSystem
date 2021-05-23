package com.gaf.feedbacksystem.dto;

import lombok.Data;

@Data
public class TrainerDto extends BaseUserDto {
    private String phone;
    private String address;
    private boolean isActive;
    private Integer idSkill;
    private String activationCode;
    private String resetPasswordCode;
    private boolean isReceiveNotification;
}
