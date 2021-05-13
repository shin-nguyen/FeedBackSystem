package com.gaf.project.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainee  {
    @SerializedName("username")
    private String userName;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("idSkill")
    private Integer idSkill;

    @SerializedName("activationCode")
    private String activationCode;

    @SerializedName("resetPasswordCode")
    private String resetPasswordCode;

//    @SerializedName("classes")
//    @Expose
//    private Collection<Class> classes = null;
}
