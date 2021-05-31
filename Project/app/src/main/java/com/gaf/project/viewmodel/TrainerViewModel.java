package com.gaf.project.viewmodel;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Trainer;
import com.gaf.project.response.TrainerReponse;
import com.gaf.project.service.TrainerService;
import com.gaf.project.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerViewModel extends ViewModel {
    private TrainerService trainerService;
    private MutableLiveData<List<Trainer>> mListTrainerLiveData;
    private List<Trainer> mListTrainer;

    public TrainerViewModel() {
        trainerService = ApiUtils.getTrainerService();
        mListTrainerLiveData = new MutableLiveData<>();

        initData();
    }

    public void initData(){
        Call<TrainerReponse> callTrainer =  trainerService.loadListTrainer();
        callTrainer.enqueue(new Callback<TrainerReponse>() {
            @Override
            public void onResponse(Call<TrainerReponse> call, Response<TrainerReponse> response) {

                if (response.isSuccessful()&& response.body()!=null){
                    mListTrainer = response.body().getTrainers();
                    mListTrainerLiveData.setValue(mListTrainer);
                }
            }
            @Override
            public void onFailure(Call<TrainerReponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<List<Trainer>> getListTrainerLiveData() {
        return mListTrainerLiveData;
    }
}
