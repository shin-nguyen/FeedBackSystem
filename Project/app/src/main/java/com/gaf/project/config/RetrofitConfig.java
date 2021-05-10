package com.gaf.project.config;

import com.gaf.project.constant.SystemConstant;
import com.gaf.project.utils.ApiUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
//        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
//
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Headers headers = request.headers().newBuilder().add("Content-Type", "application/json").build();
//                request = request.newBuilder().headers(headers).build();
//                return chain.proceed(request);
//            }
//        };
//        clientBuilder.addInterceptor(interceptor);

        return new Retrofit.Builder().baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit builderRetrofitAuth(final String token){

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Authorization", "Bearer " +token);
                builder.addHeader("Content-Type", "application/json");
                return chain.proceed(builder.build());
            }
        });

        return new Retrofit.Builder().baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}
