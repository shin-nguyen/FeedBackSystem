package com.gaf.feedbacksystem.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {


    private String username;
    private String password;
    private String role;
    private String remember;

//    public AuthenticationRequest(String username, String password, String role) {
//        this.setRole(role);
//        this.setUsername(username);
//        this.setPassword(password);
//    }
}
