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

    public Trainer(String userName, String name, String email, String password, String phone, String address, boolean isActive, Integer idSkill, String activationCode, String resetPasswordCode, boolean isReceiveNotification) {
        super(userName, name, email, password);
        this.phone = phone;
        this.address = address;
        this.isActive = isActive;
        this.idSkill = idSkill;
        this.activationCode = activationCode;
        this.resetPasswordCode = resetPasswordCode;
        this.isReceiveNotification = isReceiveNotification;
    }

    private boolean isReceiveNotification;


}
