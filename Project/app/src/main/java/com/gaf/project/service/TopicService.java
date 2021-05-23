package com.gaf.project.service;

import com.gaf.project.response.TopicResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopicService {
    @GET("topic/loadListTopic")
    Call<TopicResponse> loadListTopic();

    @GET("topic/getTopic")
    Call<TopicResponse> getTopic();
}
