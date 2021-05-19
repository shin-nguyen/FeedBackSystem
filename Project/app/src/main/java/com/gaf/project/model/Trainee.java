package com.gaf.project.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

//    public Trainee(String userName, String name, String email, String password, String phone, String address, boolean isActive, Integer idSkill, String activationCode, String resetPasswordCode, Collection<Class> classes) {
//        super(userName, name, email, password);
//        this.phone = phone;
//        this.address = address;
//        this.isActive = isActive;
//        this.idSkill = idSkill;
//        this.activationCode = activationCode;
//        this.resetPasswordCode = resetPasswordCode;
//        this.classes = classes;
//    }

//    @SerializedName("classes")
//    @Expose
//    private Collection<Class> classes = null;

}
