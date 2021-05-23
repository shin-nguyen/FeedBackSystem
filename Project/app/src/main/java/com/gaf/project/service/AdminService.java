package com.gaf.project.service;

import com.gaf.project.response.AdminResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminService {
    @GET("admin/loadListAdmin")
    Call<AdminResponse> loadListAdmin();
}
