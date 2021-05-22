package com.gaf.project.authentication;

public class AuthenticationResponse {

    private final String jwt;
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
    public AuthenticationResponse() {
        this.jwt = " ";
    }
    public String getJwt() {
        return jwt;
    }
}
