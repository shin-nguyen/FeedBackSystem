package com.gaf.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainer {
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("active")
    @Expose
    private boolean isActive;
    @SerializedName("idSkill")
    @Expose
    private Integer idSkill;
    @SerializedName("activationCode")
    @Expose
    private String activationCode;
    @SerializedName("resetPasswordCode")
    @Expose
    private String resetPasswordCode;
    @SerializedName("receiveNotification")
    @Expose
    private boolean isReceiveNotification;
}
