package com.gaf.project.service;

import com.gaf.project.response.TopicResponse;
import com.gaf.project.response.TypeFeedbackResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TypeFeedbackService {
        @GET("typefeedback/loadListTypeFeedback")
    Call<TypeFeedbackResponse> loadListTypeFeedback();
}
