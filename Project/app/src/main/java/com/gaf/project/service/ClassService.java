package com.gaf.project.service;

import com.gaf.project.model.Class;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.utils.MyHttpMessageConverter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClassService {
    @GET("class/loadListClass")
    Call<ClassResponse> loadListClass();

    @POST("class/")
    Call<Class> create(@Body Class mClass);

    @DELETE("class/{id}")
    Call<DeleteResponse> delete( @Path("id") Integer id);

    @PUT("class/")
    Call<Class> update(@Body Class mClass);
}
