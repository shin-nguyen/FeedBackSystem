package com.gaf.project.service;

import com.gaf.project.authentication.AuthenticationRequest;
import com.gaf.project.authentication.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST("login/")
    Call<AuthenticationResponse> login(@Body AuthenticationRequest authenticationRequest);
}
