package com.gaf.project.service;

import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AssignmentService {
    @Headers({"Content-Type: application/json"})
    @GET("assignment/loadListAssignment")
    Call<AssignmentResponse> loadListAssignment();

    @Headers({"Content-Type: application/json"})
    @POST("assignment/")
    Call<Assignment> create(@Body Assignment assignment);

    @Headers({"Content-Type: application/json"})
    @DELETE("assignment/{id}")
    Call<DeleteResponse> delete(@Path("id") Integer id);

    @Headers({"Content-Type: application/json"})
    @PUT("assignment/")
    Call<Assignment> update(@Body Assignment assignment);

}
