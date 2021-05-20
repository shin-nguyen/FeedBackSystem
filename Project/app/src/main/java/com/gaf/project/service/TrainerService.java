package com.gaf.project.service;

import com.gaf.project.response.ModuleResponse;
import com.gaf.project.response.TrainerReponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrainerService {
    @GET("trainer/loadListTrainer")
    Call<TrainerReponse> loadListTrainer();
}
