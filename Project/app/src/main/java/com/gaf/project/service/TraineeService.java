package com.gaf.project.service;

import com.gaf.project.model.Trainee;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TraineeService {
    @POST("trainee/loadProfile")
    Call<Trainee> loadProfile();
}
