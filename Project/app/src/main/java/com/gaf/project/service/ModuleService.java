package com.gaf.project.service;


import com.gaf.project.model.Module;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ModuleService {
    @Headers({"Content-Type: application/json"})
    @GET("module/loadModuleAdmin")
    Call<List<Module>> loadModuleAdmin(@Header("Authorization") String auth);
}
