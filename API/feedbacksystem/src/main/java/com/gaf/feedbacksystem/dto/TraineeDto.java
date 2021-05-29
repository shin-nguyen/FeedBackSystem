package com.gaf.feedbacksystem.dto;


import lombok.*;

import java.util.Collection;

@Data
public class TraineeDto extends BaseUserDto {
    private String phone;
    private String address;
    private boolean isActive;
    private Integer idSkill;
    private String activationCode;
    private String resetPasswordCode;
}
