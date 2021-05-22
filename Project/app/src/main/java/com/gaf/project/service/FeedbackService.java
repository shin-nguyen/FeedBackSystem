package com.gaf.project.service;

import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.FeedbackResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FeedbackService {

    @GET("feedback/loadListFeedback")
    Call<FeedbackResponse> loadListFeedback();

    @DELETE("feedback/{id}")
    Call<DeleteResponse> delete( @Path("id") Integer id);
}
