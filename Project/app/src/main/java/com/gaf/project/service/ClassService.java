package com.gaf.project.service;

import com.gaf.project.response.ClassResponse;
import com.gaf.project.utils.MyHttpMessageConverter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
//@Rest(rootUrl = Constants.Services.UserApi.ServiceBase, converters = {MyHttpMessageConverter.class})
public interface ClassService {
    @Headers({"Content-Type: application/json"})
    @GET("class/loadListClass")
    Call<ClassResponse> loadListClass(@Header("Authorization") String auth);
}
