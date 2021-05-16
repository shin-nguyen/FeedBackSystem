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
import retrofit2.http.Path;

//@Rest(rootUrl = Constants.Services.UserApi.ServiceBase, converters = {MyHttpMessageConverter.class})
public interface ClassService {
    @Headers({"Content-Type: application/json"})
    @GET("class/loadListClass")
    Call<ClassResponse> loadListClass(
            @Header("Authorization") String auth
    );

    @Headers({"Content-Type: application/json"})
    @POST("class/")
    Call<Class> create(@Header("Authorization") String auth, @Body Class mClass);

    @Headers({"Content-Type: application/json"})
    @DELETE("class/{id}")
    Call<DeleteResponse> delete(@Header("Authorization") String auth, @Path("id") Integer id);
}
