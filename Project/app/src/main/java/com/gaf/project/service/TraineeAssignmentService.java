package com.gaf.project.service;

import com.gaf.project.response.AddTraineeAssignmentResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TraineeAssignmentService {
    @POST("traineeAssignment/{username}/{code}")
    Call<AddTraineeAssignmentResponse> create(@Path("username") String username,
                                              @Path("code") String code);
}
