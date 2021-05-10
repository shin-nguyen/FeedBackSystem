package com.gaf.project.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends BaseUser{
    private String userName;
    private String name;
    private String email;
    private String password;
}