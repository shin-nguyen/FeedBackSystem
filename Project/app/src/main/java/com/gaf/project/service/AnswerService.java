package com.gaf.project.service;

import com.gaf.project.response.AnswerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AnswerService {
    @GET("class/{idClass}/{idModule}")
    Call<AnswerResponse> loadListAnswer(@Path("idClass") Integer idClass, @Path("idModule") Integer idModule);

    @GET("class/{idClass}/{idModule}/{idQuestion}")
    Call<AnswerResponse> loadListAnswerByQuestion(@Path("idClass") Integer idClass, @Path("idModule") Integer idModule, @Path("idQuestion") Integer idQuestion);

    @GET("class/{topicID}")
    Call<AnswerResponse> loadListAnswerByTopic(@Path("topicID") Integer topicID);
}
