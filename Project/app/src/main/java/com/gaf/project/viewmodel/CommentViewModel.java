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
import com.gaf.project.model.Class;
import com.gaf.project.model.Comment;
import com.gaf.project.model.Question;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.service.CommentService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentViewModel extends ViewModel {
    private CommentService commentService;

    public CommentViewModel() {
        commentService = ApiUtils.getCommentService();
    }

    public MutableLiveData<String> add(Comment comment) {
        MutableLiveData<String> actionStatus = new MutableLiveData<>();

        Call<Comment> call = commentService.save(comment);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    actionStatus.setValue(SystemConstant.SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                actionStatus.setValue(SystemConstant.FAIL);
                Log.e("Error", t.getLocalizedMessage());
            }
        });
        return actionStatus;
    }
}
