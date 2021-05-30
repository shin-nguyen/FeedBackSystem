package com.gaf.project.service;

import com.gaf.project.model.TraineeAssignment;
import com.gaf.project.response.AddTraineeAssignment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TraineeAssignmentService {
    @POST("traineeAssignment/{username}/{code}")
    Call<AddTraineeAssignment> create(@Path("username") String username, @Path("code") String code);
}
