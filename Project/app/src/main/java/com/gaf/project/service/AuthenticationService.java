package com.gaf.project.service;

import com.gaf.project.authentication.AuthenticationRequest;
import com.gaf.project.authentication.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthenticationService {
    @Headers({"Content-Type: application/json"})
    @POST("login/")
    Call<AuthenticationResponse> login(@Body AuthenticationRequest authenticationRequest);
}
