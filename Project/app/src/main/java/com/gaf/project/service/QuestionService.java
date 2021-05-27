package com.gaf.project.service;

import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.model.Question;
import com.gaf.project.response.AnswerResponse;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.QuestionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface QuestionService {
    @GET("question/loadListQuestion")
    Call<QuestionResponse> loadListQuestion();

    @GET("question/loadListQuestionByTopic/{topicId}")
    Call<QuestionResponse> loadListQuestionByTopic(@Path("topicId") Integer id);

    @POST("question/")
    Call<Question> create(@Body Question question);

    @DELETE("question/{id}")
    Call<DeleteResponse> delete( @Path("id") Integer id);

    @PUT("question/")
    Call<Question> update(@Body Question question);
}
