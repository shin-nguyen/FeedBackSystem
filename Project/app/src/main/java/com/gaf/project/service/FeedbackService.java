package com.gaf.project.service;

import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.FeedbackResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FeedbackService {
    @GET("feedback/getListFeedback")
    Call<FeedbackResponse> getListFeedback();

    @DELETE("feedback/{id}")
    Call<DeleteResponse> delete( @Path("id") Integer id);

    @POST("feedback/")
    Call<Feedback> create(@Body Feedback mFeedback);

    @PUT("feedback/")
    Call<Feedback> update(@Body Feedback mFeedback);
}
