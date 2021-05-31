package com.gaf.project.service;

import com.gaf.project.model.Comment;
import com.gaf.project.model.Module;
import com.gaf.project.response.CommentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentService {
    @GET("comment/{idClass}/{idModule}")
    Call<CommentResponse> loadListComment(@Path("idClass") Integer idClass, @Path("idModule") Integer idModule);
    @POST("comment/")
    Call<Comment> save(@Body Comment comment);

}
