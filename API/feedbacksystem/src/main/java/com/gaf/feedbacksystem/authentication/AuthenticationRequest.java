package com.gaf.feedbacksystem.authentication;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {


    private String username;
    private String password;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //need default constructor for JSON Parsing
    public AuthenticationRequest()
    {

    }

    public AuthenticationRequest(String username, String password, String role) {
        this.setRole(role);
        this.setUsername(username);
        this.setPassword(password);
    }
}
