package com.gaf.project.model;


import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
