package com.gaf.project.service;

import com.gaf.project.model.Class;
import com.gaf.project.model.Trainee;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClassService {
    @GET("class/loadListClass")
    Call<ClassResponse> loadListClass();

    @GET("class/loadListClassByTrainer")
    Call<ClassResponse> loadListClassByTrainer();

    @GET("class/loadListClassByTrainee")
    Call<ClassResponse> loadListClassByTrainee();

    @POST("class/")
    Call<Class> create(@Body Class mClass);

    @DELETE("class/{id}")
    Call<DeleteResponse> delete( @Path("id") Integer id);

    @PUT("class/updateTrainee/{idOld}/{idNew}/{idTrainee}")
    Call<Class> updateTrainee(@Path(value = "idOld") Integer idOld,
                                @Path(value = "idNew") Integer idNew,
                              @Path(value = "idTrainee") String idTrainee);

    @PUT("class/deleteTrainee/{idTrainee}/{idClass}")
    Call<Class> deleteTrainee(@Path("idTrainee") String idTrainee,@Path("idClass") Integer idClass);

    @PUT("class/")
    Call<Class> update(@Body Class mClass);
}
