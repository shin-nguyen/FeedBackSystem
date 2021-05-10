package com.gaf.project.service;

import com.gaf.project.model.Assignment;
import com.gaf.project.model.Module;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ModuleService {
    @GET("module/loadModuleAdmin")
    Call<List<Module>> loadModuleAdmin();
}
