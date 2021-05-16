package com.gaf.project.config;

import com.gaf.project.LocalDateDeserializer;
import com.gaf.project.LocalDateSerializer;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.utils.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;

import okhttp3.Headers;
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

    public Retrofit  builderRetrofitAuth(String token){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());

        Gson gson = gsonBuilder
                .setDateFormat("dd/MM/yyyy")
                .setPrettyPrinting()
                .create();
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(logging)
                .build();


        httpClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Authorization", "Bearer " +token);
                return chain.proceed(builder.build());
            }
        });

        return new Retrofit.Builder().baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        gson
                ))
                .client(httpClient)
                .build();
    }
}
