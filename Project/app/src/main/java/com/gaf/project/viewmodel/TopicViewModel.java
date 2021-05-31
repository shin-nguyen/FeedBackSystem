package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.service.QuestionService;
import com.gaf.project.service.TopicService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicViewModel extends ViewModel {
    private TopicService topicService;
    private MutableLiveData<List<Topic>> mListTopicLiveData;
    private List<Topic> mListTopic;

    public TopicViewModel() {
        topicService = ApiUtils.getTopicService();
        mListTopicLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        mListTopic = new ArrayList<>();
        Call<TopicResponse> callTopic = topicService.loadListTopic();
        callTopic.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                    if (response.isSuccessful()&& response.body()!=null){
                        mListTopic= response.body().getTopic();
                        mListTopicLiveData.setValue(mListTopic);
                    }
            }
            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<List<Topic>> getListTopicLiveData() {
        return mListTopicLiveData;
    }
}
