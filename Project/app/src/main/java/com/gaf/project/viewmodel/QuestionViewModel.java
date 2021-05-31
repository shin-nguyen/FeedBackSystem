package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Question;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.QuestionResponse;
import com.gaf.project.service.QuestionService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionViewModel extends ViewModel {

    private QuestionService questionService;
    private MutableLiveData<List<Question>> mListQuestionLiveData;
    private List<Question> mListQuestion;
    private Boolean actionStatus;

    public QuestionViewModel() {
        questionService = ApiUtils.getQuestionService();
        mListQuestionLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        mListQuestion = new ArrayList<>();
        Call<QuestionResponse> call =  questionService.loadListActiveQuestion();
        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    mListQuestion = response.body().getQuestions();
                    mListQuestionLiveData.setValue(mListQuestion);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public void addQuestion(Question question){
        setActionStatus(true);
        Call<Question> call = questionService.create(question);
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful() && response.body() != null) {
                    initData();
                    setActionStatus(false);
                    Log.e("Success", "Success");
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }

    public void updateQuestion(Question question){
        setActionStatus(true);
        Call<Question> call = questionService.update(question);
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful() && response.body() != null) {
                    initData();
                    setActionStatus(false);
                    Log.e("Success", "Success");
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }

    public void deleteQuestion(Question question){
        setActionStatus(true);
        Call<DeleteResponse> call =  questionService.delete(question.getQuestionID());
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()&&response.body().getDeleted()){
                    initData();
                    Log.e("Success", "Success");
                }
            }
            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                setActionStatus(false);
            }
        });
    }

    public MutableLiveData<List<Question>> getListQuestionLiveData() {
        return mListQuestionLiveData;
    }

    public Boolean getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Boolean actionStatus) {
        this.actionStatus = actionStatus;
    }
}
