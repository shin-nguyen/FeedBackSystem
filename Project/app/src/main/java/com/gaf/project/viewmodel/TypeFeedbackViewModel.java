package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.TypeFeedback;
import com.gaf.project.response.TypeFeedbackResponse;
import com.gaf.project.service.TypeFeedbackService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeFeedbackViewModel extends ViewModel {

    private TypeFeedbackService typeFeedbackService;
    private MutableLiveData<List<TypeFeedback>> mListTypeFeedbackLiveData;
    private List<TypeFeedback> mListTypeFeedback;

    public TypeFeedbackViewModel() {
        typeFeedbackService = ApiUtils.getTypeFeedbackService();
        mListTypeFeedbackLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        mListTypeFeedback = new ArrayList<>();
        Call<TypeFeedbackResponse> callTopic = typeFeedbackService.loadListTypeFeedback();
        callTopic.enqueue(new Callback<TypeFeedbackResponse>() {
            @Override
            public void onResponse(Call<TypeFeedbackResponse> call, Response<TypeFeedbackResponse> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    mListTypeFeedback= response.body().getTypeFeedbacks();
                    mListTypeFeedbackLiveData.setValue(mListTypeFeedback);
                }
            }
            @Override
            public void onFailure(Call<TypeFeedbackResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<List<TypeFeedback>> getListTypeFeedbackLiveData(){
        return mListTypeFeedbackLiveData;
    }
}
