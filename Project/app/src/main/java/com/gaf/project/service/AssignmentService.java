package com.gaf.project.service;

import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AssignmentService {
    @GET("assignment/loadListAssignment")
    Call<AssignmentResponse> loadListAssignment();

    @GET("assignment/loadListAssignmentByTrainee")
    Call<AssignmentResponse> loadListAssignmentByTrainee();

    @GET("assignment/loadListAssignmentByTrainer")
    Call<AssignmentResponse> loadListAssignmentByTrainer();

    @POST("assignment/")
    Call<Assignment> create(@Body Assignment assignment);

    @DELETE("assignment/{idClass}/{idModule}/{userName}")
    Call<DeleteResponse> delete(@Path("idClass") Integer idClass, @Path("idModule") Integer idModule, @Path("userName") String userName );

    @PUT("assignment/{userName}")
    Call<Assignment> update(@Path("userName") String userName,@Body Assignment newAssignment);

}
