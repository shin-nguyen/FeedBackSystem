package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Assignment;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentViewModel extends ViewModel {
    private AssignmentService assignmentService;
    private MutableLiveData<List<Assignment>> mListAssignmentLiveData;
    private MutableLiveData<List<Assignment>> mListAssignmentOfTrainerLiveData;
    private List<Assignment> mListAssignment;
    private List<Assignment> mListAssignmentOfTrainer;
    private Boolean actionStatus;

    public AssignmentViewModel() {
        assignmentService = ApiUtils.getAssignmentService();
        mListAssignmentLiveData = new MutableLiveData<>();
        mListAssignmentOfTrainerLiveData = new MutableLiveData<>();

        initData();
        initDataByTrainer();
    }

    public void initData(){
        Call<AssignmentResponse> callAll =  assignmentService.loadListAssignment();
        callAll.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    mListAssignment = response.body().getAssignments();
                    mListAssignmentLiveData.setValue(mListAssignment);
                    Log.e("Success","Assignment get success");
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public void initDataByTrainer(){
        Call<AssignmentResponse> call =  assignmentService.loadListAssignmentByTrainer();
        call.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    mListAssignmentOfTrainer = response.body().getAssignments();
                    mListAssignmentOfTrainerLiveData.setValue(mListAssignmentOfTrainer);
                    Log.e("Success","Assignment get success");
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public void addAssignment(Assignment assignment){
        setActionStatus(true);
        Call<Assignment> call = assignmentService.create(assignment);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    setActionStatus(true);
                    initData();
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                setActionStatus(false);
            }
        });
    }

    public void updateAssignment(String trainerName, Assignment assignment){
        setActionStatus(true);
        Call<Assignment> call =  assignmentService.update(trainerName,assignment);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    setActionStatus(true);
                    initData();
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                setActionStatus(false);
            }
        });
    }

    public void deleteAssignment(Assignment assignment){
        setActionStatus(true);
        Call<DeleteResponse> call =  assignmentService.delete(assignment.getMClass().getClassID(),
                assignment.getModule().getModuleID(),
                assignment.getTrainer().getUserName());
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()&&response.body().getDeleted()){
                    setActionStatus(true);
                    initData();
                }
            }
            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                setActionStatus(false);
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<List<Assignment>> getListAssignmentLiveData() {
        return mListAssignmentLiveData;
    }

    public MutableLiveData<List<Assignment>> getListAssignmentOfTrainerLiveData() {
        return mListAssignmentOfTrainerLiveData;
    }

    public Boolean getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Boolean actionStatus) {
        this.actionStatus = actionStatus;
    }

    public List<Assignment> getListAssignment() {
        return mListAssignment;
    }
}
