package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Class;
import com.gaf.project.model.Enrollment;
import com.gaf.project.model.Trainee;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
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
    private List<Class> mlistClass;

    public EnrollmentViewModel(){
        classService = ApiUtils.getClassService();
        mListEnrollmentLiveData = new MutableLiveData<>();
        mListClassLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        mListEnrollment = new ArrayList<>();
        mlistClass = new ArrayList<>();
        Call<ClassResponse> call =  classService.loadListClass();
        call.enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {
                mlistClass = response.body().getClasss();
                if (mlistClass!=null) {
                    for (Class mClass:mlistClass) {
                        for (Trainee trainee : mClass.getTrainees())
                            mListEnrollment.add(new Enrollment(mClass,trainee));
                    }
                    mListEnrollmentLiveData.setValue(mListEnrollment);
                    mListClassLiveData.setValue(mlistClass);
                }
            }

            @Override
            public void onFailure(Call<ClassResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<String> update(int oldClass, int newClass, String username) {
        MutableLiveData<String> actionStatus = new MutableLiveData<>();

        Call<Class> enrollmentCall =  classService.updateTrainee(oldClass, newClass, username);
            enrollmentCall.enqueue(new Callback<Class>() {
                @Override
                public void onResponse(Call<Class> call, Response<Class> response) {
                    if (response.isSuccessful()&&response.body()!=null) {
                        actionStatus.setValue(SystemConstant.SUCCESS);
                        initData();
                    }
                }

                @Override
                public void onFailure(Call<Class> call, Throwable t) {
                    actionStatus.setValue(SystemConstant.FAIL);
                    Log.e("Error",t.getLocalizedMessage());
                }
            });
        return actionStatus;

    }

    public MutableLiveData<String> delete(String traineeUsername, int classId){
        MutableLiveData<String> actionStatus = new MutableLiveData<>();

        Call<Class> call =  classService.deleteTrainee(traineeUsername, classId);
        call.enqueue(new Callback<Class>() {
            @Override
            public void onResponse(Call<Class> call, Response<Class> response) {
                if (response.isSuccessful()&&response.body() != null){
                    actionStatus.setValue(SystemConstant.SUCCESS);
                    initData();
                }
            }
            @Override
            public void onFailure(Call<Class> call, Throwable t) {
                actionStatus.setValue(SystemConstant.FAIL);
                Log.e("Error",t.getLocalizedMessage());
            }
        });

        return actionStatus;
    }

    public MutableLiveData<List<Enrollment>> getListEnrollmentLiveData(){
        return mListEnrollmentLiveData;
    }

    public MutableLiveData<List<Class>> getListClassLiveData(){
        return mListClassLiveData;
    }
}
