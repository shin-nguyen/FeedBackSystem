package com.gaf.project.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.model.Admin;
import com.gaf.project.model.Question;
import com.gaf.project.response.AdminResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.QuestionResponse;
import com.gaf.project.service.AdminService;
import com.gaf.project.service.QuestionService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewModel extends ViewModel {

    private AdminService adminService;
    private MutableLiveData<List<Admin>> mListAdminLiveData;
    private List<Admin> mListAmin;
    private Boolean actionStatus;

    public AdminViewModel() {
        adminService = ApiUtils.getAdminService();
        mListAdminLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        mListAmin = new ArrayList<>();
        Call<AdminResponse> call =  adminService.loadListAdmin();
        call.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    mListAmin = response.body().getAdmins();
                    mListAdminLiveData.setValue(mListAmin);
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }


    public MutableLiveData<List<Admin>> getmListAdminLiveData() {
        return mListAdminLiveData;
    }

    public Boolean getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Boolean actionStatus) {
        this.actionStatus = actionStatus;
    }
}
