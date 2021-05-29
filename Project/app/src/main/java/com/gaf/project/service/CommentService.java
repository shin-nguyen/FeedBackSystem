package com.gaf.project.service;

import com.gaf.project.response.CommentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentService {
    @GET("class/{idClass}/{idModule}")
    Call<CommentResponse> loadListComment(@Path("idClass") Integer idClass, @Path("idModule") Integer idModule);
}
