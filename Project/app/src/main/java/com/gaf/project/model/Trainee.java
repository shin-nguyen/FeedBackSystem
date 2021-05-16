package com.gaf.project.model;


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
public class Trainee  extends BaseUser {


    private String phone;
    private String address;
    private boolean isActive;
    private Integer idSkill;
    private String activationCode;
    private String resetPasswordCode;

    private Collection<Class> classes;

    public Trainee(String userName, String name, String email, String password, String phone, String address, boolean isActive, Integer idSkill, String activationCode, String resetPasswordCode, Collection<Class> classes) {
        super(userName, name, email, password);
        this.phone = phone;
        this.address = address;
        this.isActive = isActive;
        this.idSkill = idSkill;
        this.activationCode = activationCode;
        this.resetPasswordCode = resetPasswordCode;
        this.classes = classes;
    }
}
