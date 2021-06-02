package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.FeedbackResponse;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackViewModel extends ViewModel {
    private FeedbackService feedbackService;
    private MutableLiveData<List<Feedback>> mListFeedBackLiveData;
    private List<Feedback> mListFeedBack;
    private Boolean status;

    public FeedBackViewModel() {
        feedbackService = ApiUtils.getFeedbackService();
        mListFeedBackLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        mListFeedBack = new ArrayList<>();
        Call<FeedbackResponse> call =  feedbackService.getListFeedback();
        call.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    mListFeedBack = response.body().getFeedbacks();
                    mListFeedBackLiveData.setValue(mListFeedBack);
                }
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<String> add(Feedback mFeedback) {
        MutableLiveData<String> actionStatus = new MutableLiveData<>();

        Call<Feedback> call = feedbackService.create(mFeedback);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful() && response.body() != null) {
                    actionStatus.setValue(SystemConstant.SUCCESS);
                    initData();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                actionStatus.setValue(SystemConstant.FAIL);
                Log.e("Error", t.getLocalizedMessage());
            }
        });
        return actionStatus;
    }

    public MutableLiveData<String> update(Feedback mFeedback) {
        MutableLiveData<String> actionStatus = new MutableLiveData<>();

        Call<Feedback> call = feedbackService.update(mFeedback);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if (response.isSuccessful() && response.body() != null) {
                    actionStatus.setValue(SystemConstant.SUCCESS);
                    initData();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                actionStatus.setValue(SystemConstant.FAIL);
                Log.e("Error", t.getLocalizedMessage());
            }
        });
        return actionStatus;

    }

    public MutableLiveData<String> delete(Feedback mFeedback){
        MutableLiveData<String> actionStatus = new MutableLiveData<>();

        Call<DeleteResponse> call =  feedbackService.delete(mFeedback.getFeedbackID());
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()&&response.body().getDeleted()){
                    actionStatus.setValue(SystemConstant.SUCCESS);
                    initData();
                }
            }
            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                actionStatus.setValue(SystemConstant.FAIL);
                Log.e("Error",t.getLocalizedMessage());
            }
        });

        return actionStatus;
    }

    public MutableLiveData<List<Feedback>> getListFeedBackLiveData() {
        return mListFeedBackLiveData;
    }
}
