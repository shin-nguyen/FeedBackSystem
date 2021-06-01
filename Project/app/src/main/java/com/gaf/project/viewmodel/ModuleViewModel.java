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
import com.gaf.project.model.Module;
import com.gaf.project.model.Question;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ModuleService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleViewModel extends ViewModel {
    private ModuleService moduleService;
    private MutableLiveData<List<Module>> mListModuleLiveData;
    private List<Module> mListModule = new ArrayList<>();

    public ModuleViewModel() {
        moduleService = ApiUtils.getModuleService();
        mListModuleLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        String userRole = SessionManager.getInstance().getUserRole();
        Call<ModuleResponse> call = null;
        if(userRole.equals(SystemConstant.ADMIN_ROLE)){
            call =  moduleService.loadModuleAdmin();
        }else if(userRole.equals(SystemConstant.TRAINER_ROLE)){
            call =  moduleService.loadModuleTrainer();
        }else if(userRole.equals(SystemConstant.TRAINEE_ROLE)){
            call =  moduleService.loadModuleTrainee();
        }
        setAdapter(call);

    }

    public MutableLiveData<String> add(Module module){
        MutableLiveData<String> actionStatus = new MutableLiveData<>();
        Call<Module> call = moduleService.create(module);
        call.enqueue(new Callback<Module>() {
            @Override
            public void onResponse(Call<Module> call, Response<Module> response) {
                if (response.isSuccessful() && response.body() != null) {
                    actionStatus.setValue(SystemConstant.SUCCESS);
                    initData();
                }
            }
            @Override
            public void onFailure(Call<Module> call, Throwable t) {
                actionStatus.setValue(SystemConstant.FAIL);
                Log.e("Error", t.getLocalizedMessage());
            }
        });
        return actionStatus;
    }

    public MutableLiveData<String> update(Module module){
        MutableLiveData<String> actionStatus = new MutableLiveData<>();
        Call<Module> call = moduleService.update(module);
        call.enqueue(new Callback<Module>() {
            @Override
            public void onResponse(Call<Module> call, Response<Module> response) {
                if (response.isSuccessful() && response.body() != null) {
                    actionStatus.setValue(SystemConstant.SUCCESS);
                    initData();
                }
            }

            @Override
            public void onFailure(Call<Module> call, Throwable t) {
                actionStatus.setValue(SystemConstant.FAIL);
                Log.e("Error", t.getLocalizedMessage());
            }
        });
        return actionStatus;
    }

    public MutableLiveData<String> delete(Module module){
        MutableLiveData<String> actionStatus = new MutableLiveData<>();
        Call<DeleteResponse> call =  moduleService.delete(module.getModuleID());
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

    private void setAdapter(Call<ModuleResponse> call){
        call.enqueue(new Callback<ModuleResponse>() {
            @Override
            public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {

                if (response.isSuccessful()&& response.body()!=null){
                    mListModule = response.body().getModules();
                    mListModuleLiveData.setValue(mListModule);
                }
            }
            @Override
            public void onFailure(Call<ModuleResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }


    public MutableLiveData<List<Module>> getmListModuleLiveData() {
        return mListModuleLiveData;
    }
}
