package com.gaf.project.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Class;
import com.gaf.project.model.Comment;
import com.gaf.project.model.Question;
import com.gaf.project.response.AnswerResponse;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.AnswerService;
import com.gaf.project.service.ClassService;
import com.gaf.project.service.CommentService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswerViewModel extends ViewModel {
    private AnswerService answerService;

    public AnswerViewModel() {
        answerService = ApiUtils.getAnswerService();
    }

    public MutableLiveData<String> addAll(List<Answer> answers) {
        MutableLiveData<String> actionStatus = new MutableLiveData<>();

        Call<AnswerResponse> call = answerService.addAll(answers);
        call.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    actionStatus.setValue(SystemConstant.SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                actionStatus.setValue(SystemConstant.FAIL);
                Log.e("Error", t.getLocalizedMessage());
            }
        });
        return actionStatus;
    }
}
