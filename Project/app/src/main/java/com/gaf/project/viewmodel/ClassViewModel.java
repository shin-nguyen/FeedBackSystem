package com.gaf.project.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Class;
import com.gaf.project.model.Question;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassViewModel extends ViewModel {
    private ClassService classService;
    private MutableLiveData<List<Class>> mListClassLiveData;
    private List<Class> mListClass;
    private Boolean actionStatus;


    public ClassViewModel() {
        classService = ApiUtils.getClassService();
        mListClassLiveData = new MutableLiveData<>();

        initData();
    }

    public void initData(){
        String userRole = SessionManager.getInstance().getUserRole();
        Call<ClassResponse> call  = null;
        if (userRole.equals(SystemConstant.ADMIN_ROLE)){
            call  =  classService.loadListClass();
        }
        if(userRole.equals(SystemConstant.TRAINER_ROLE)){
             call =  classService.loadListClassByTrainer();
        }else if(userRole.equals(SystemConstant.TRAINEE_ROLE)){
             call =  classService.loadListClassByTrainee();
        }
        setAdapter(call);
    }
    private void setAdapter(Call<ClassResponse> call){

        call.enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    mListClass = response.body().getClasss();
                    mListClassLiveData.setValue(mListClass);
                }
            }

            @Override
            public void onFailure(Call<ClassResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public void deleted(Class mClass){
        setActionStatus(false);

        Call<DeleteResponse> call =  classService.delete(mClass.getClassID());
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()&&response.body().getDeleted()){
                    initData();
                    setActionStatus(true);
                }
            }
            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                setActionStatus(false);
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<List<Class>> getListClassLiveData() {
        return mListClassLiveData;
    }

    public Boolean getActionStatus() {
        return actionStatus;
    }
    public void setActionStatus(Boolean actionStatus) {
        this.actionStatus = actionStatus;
    }

    public void update(Class mClass) {
        setActionStatus(false);
        Call<Class> call = classService.create(mClass);
        call.enqueue(new Callback<Class>() {
            @Override
            public void onResponse(Call<Class> call, Response<Class> response) {
                if (response.isSuccessful() && response.body() != null) {
                    initData();
                    setActionStatus(true);
                }
            }

            @Override
            public void onFailure(Call<Class> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }

    public void add(Class mClass) {
        setActionStatus(true);
        Call<Class> call = classService.create(mClass);
        call.enqueue(new Callback<Class>() {
            @Override
            public void onResponse(Call<Class> call, Response<Class> response) {
                if (response.isSuccessful() && response.body() != null) {
                    initData();
                    setActionStatus(false);
                }
            }

            @Override
            public void onFailure(Call<Class> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }
}
