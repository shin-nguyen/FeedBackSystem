package com.gaf.project.viewmodel;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Module;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ModuleService;
import com.gaf.project.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleViewModel extends ViewModel {
    private ModuleService moduleService;
    private MutableLiveData<List<Module>> mListModuleLiveData;
    private List<Module> mListModule;

    public ModuleViewModel() {
        moduleService = ApiUtils.getModuleService();
        mListModuleLiveData = new MutableLiveData<>();

        initData();
    }

    public void initData(){
        Call<ModuleResponse> callModule =  moduleService.loadModuleAdmin();
        callModule.enqueue(new Callback<ModuleResponse>() {
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

    public MutableLiveData<List<Module>> getListModuleLiveData() {
        return mListModuleLiveData;
    }
}
