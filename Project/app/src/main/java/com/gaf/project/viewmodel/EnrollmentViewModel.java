package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Class;
import com.gaf.project.model.Enrollment;
import com.gaf.project.model.Trainee;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollmentViewModel extends ViewModel {

    private ClassService classService;
    private MutableLiveData<List<Enrollment>> mListEnrollmentLiveData;
    private MutableLiveData<List<Class>> mListClassLiveData;
    private List<Enrollment> mListEnrollment;
    private List<Class> mListClass;

    public EnrollmentViewModel(){
        classService = ApiUtils.getClassService();
        mListEnrollmentLiveData = new MutableLiveData<>();
        mListClassLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        mListEnrollment = new ArrayList<>();
        mListClass= new ArrayList<>();
        Call<ClassResponse> call =  classService.loadListClass();
        call.enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {
                mListClass = response.body().getClasss();
                if (mListClass!=null) {
                    for (Class mClass:mListClass) {
                        for (Trainee trainee : mClass.getTrainees())
                            mListEnrollment.add(new Enrollment(mClass,trainee));
                    }
                    mListEnrollmentLiveData.setValue(mListEnrollment);
                    mListClassLiveData.setValue(mListClass);
                }
            }

            @Override
            public void onFailure(Call<ClassResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<List<Enrollment>> getListEnrollmentLiveData(){
        return mListEnrollmentLiveData;
    }

    public MutableLiveData<List<Class>> getListClassLiveData(){
        return mListClassLiveData;
    }
}
