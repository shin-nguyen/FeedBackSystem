package com.gaf.feedbacksystem.dto;


import lombok.*;

import java.util.Collection;

@Data
public class TraineeDto{
    private  String userName;
    private String name;
    private  String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraineeDto that = (TraineeDto) o;

        return userName.equals(that.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    private String password;
    private String phone;
    private String address;
    private boolean isActive;
    private Integer idSkill;
    private String activationCode;
    private String resetPasswordCode;
}
