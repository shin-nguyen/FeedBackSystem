package com.gaf.project.service;

import com.gaf.project.model.Class;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.utils.MyHttpMessageConverter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//@Rest(rootUrl = Constants.Services.UserApi.ServiceBase, converters = {MyHttpMessageConverter.class})
public interface ClassService {
    @Headers({"Content-Type: application/json"})
    @GET("class/loadListClass")
    Call<ClassResponse> loadListClass(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json"})
    @POST("class/")
    Call<Class> create(@Header("Authorization") String auth, @Body Class mClass);
}
