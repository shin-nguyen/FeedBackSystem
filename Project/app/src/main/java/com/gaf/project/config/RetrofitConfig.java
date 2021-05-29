package com.gaf.project.config;

import com.gaf.project.constant.SystemConstant;
import com.gaf.project.utils.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static RetrofitConfig instance;

    public  static RetrofitConfig getInstance(){
        if (instance==null){
            instance = new RetrofitConfig();
        }
        return  instance;
    }

    public Retrofit buildRetrofit(){
        return new Retrofit.Builder().baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static  Retrofit  builderRetrofitAuth(){
        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder
                .setDateFormat("dd/MM/yyyy")
                .setPrettyPrinting()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);

        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().
                    addHeader("Authorization", "Bearer " +
                            SystemConstant.authenticationResponse.getJwt())
                    .addHeader("Content-Type","application/json")
                    .build();
            return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(ApiUtils.BASE_URL).client(httpClient.build()).build();

    }
}
