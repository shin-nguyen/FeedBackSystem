package com.gaf.project.service;

import com.gaf.project.model.Assignment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface AssignmentService {
    @GET("/assignment/loadAssignment")
    Call<List<Assignment>> getListAssignment();
}
