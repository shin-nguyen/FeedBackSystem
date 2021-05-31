package com.gaf.project.viewmodel;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Class;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassViewModel extends ViewModel {
    private ClassService classService;
    private MutableLiveData<List<Class>> mListClassLiveData;
    private List<Class> mListClass;

    public ClassViewModel() {
        classService = ApiUtils.getClassService();
        mListClassLiveData = new MutableLiveData<>();

        initData();
    }

    public void initData(){
        Call<ClassResponse> callClass =  classService.loadListClass();
        callClass.enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {

                if (response.isSuccessful()&& response.body()!=null){
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

    public MutableLiveData<List<Class>> getListClassLiveData() {
        return mListClassLiveData;
    }
}
