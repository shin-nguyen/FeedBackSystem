package com.gaf.project.service;


import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.ModuleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ModuleService {
    @GET("module/loadModuleAdmin")
    Call<ModuleResponse> loadModuleAdmin();

    @DELETE("module/{id}")
    Call<DeleteResponse> delete( @Path("id") Integer id);

    @GET("module/loadModuleTrainer")
    Call<ModuleResponse> loadModuleTrainer();

    @GET("module/loadModuleTrainee")
    Call<ModuleResponse> loadModuleTrainee();

    @POST("module/")
    Call<Module> create(@Body Module mModule);

    @PUT("module/")
    Call<Module> update(@Body Module mModule);
}
