package com.gaf.project.service;

import com.gaf.project.model.Class;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClassService {
    @GET("class/loadListClass")
    Call<ClassResponse> loadListClass();

    @GET("class/loadListClassByTrainer")
    Call<ClassResponse> loadListClassByTrainer();

    @GET("class/loadListClassByTrainee")
    Call<ClassResponse> loadListClassByTrainee();

    @POST("class/")
    Call<Class> create(@Body Class mClass);

    @DELETE("class/{id}")
    Call<DeleteResponse> delete( @Path("id") Integer id);

    @PUT("class/")
    Call<Class> update(@Body Class mClass);

    @PUT("class/{id}")
    Call<Class> deleteTrainee(@Path("id") String id,@Body Class mClass);

}
