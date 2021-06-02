package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Trainee;
import com.gaf.project.model.Trainer;
import com.gaf.project.service.TraineeService;
import com.gaf.project.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeViewModel extends ViewModel {
    private TraineeService traineeService;
    private MutableLiveData<Trainee> traineeMutableLiveData;
    private Trainee trainee;
    public TraineeViewModel() {
        traineeService = ApiUtils.getTraineeService();
        traineeMutableLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        Call<Trainee> callTrainee =  traineeService.loadProfile();
        callTrainee.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {

                if (response.isSuccessful()&& response.body()!=null){
                    trainee = response.body();
                    traineeMutableLiveData.setValue(trainee);
                }
            }
            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<Trainee> getTraineeMutableLiveData() {
        return traineeMutableLiveData;
    }

}
