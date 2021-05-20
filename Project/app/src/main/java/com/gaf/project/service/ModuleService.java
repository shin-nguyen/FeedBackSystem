package com.gaf.project.service;


import com.gaf.project.model.Module;
import com.gaf.project.response.ModuleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ModuleService {
    @GET("module/loadModuleAdmin")
    Call<ModuleResponse> loadModuleAdmin();
}
