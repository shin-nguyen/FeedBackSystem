package com.gaf.project.service;

<<<<<<< HEAD
import com.gaf.project.model.Comment;
import com.gaf.project.response.AddTraineeAssignmentResponse;
=======
>>>>>>> parent of d8a46df (fix conflict)
import com.gaf.project.response.CommentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentService {
    @GET("class/{idClass}/{idModule}")
    Call<CommentResponse> loadListComment(@Path("idClass") Integer idClass, @Path("idModule") Integer idModule);
<<<<<<< HEAD

    @GET("comment/loadListCommentByTrainee/{idClass}/{idModule}")
    Call<AddTraineeAssignmentResponse> loadListCommentByTrainee(@Path("idClass") Integer idClass, @Path("idModule") Integer idModule);

    @POST("comment/")
    Call<Comment> save(@Body Comment comment);

=======
>>>>>>> parent of d8a46df (fix conflict)
}
