package com.gaf.project.viewmodel;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gaf.project.LoginActivity;
import com.gaf.project.MainActivity;
import com.gaf.project.authentication.AuthenticationRequest;
import com.gaf.project.authentication.AuthenticationResponse;
import com.gaf.project.model.Trainee;
import com.gaf.project.model.Trainer;
import com.gaf.project.service.AuthenticationService;
import com.gaf.project.service.TraineeService;
import com.gaf.project.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationViewModel extends ViewModel {
    private AuthenticationService authenticationService;
    private MutableLiveData<AuthenticationResponse> authenticationResponseMutableLiveData;
    private AuthenticationResponse authenticationResponse;
    private Boolean actionStatus;

    public AuthenticationViewModel() {
        authenticationService = ApiUtils.getAuthenticationService();
        authenticationResponseMutableLiveData = new MutableLiveData<>();
    }

    public void initData(AuthenticationRequest authenticationRequest){
        setActionStatus(false);
        authenticationService.login(authenticationRequest)
                .enqueue( new Callback<AuthenticationResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                        if (response.isSuccessful()&&response.body()!=null){
                            authenticationResponse = response.body();
                            authenticationResponseMutableLiveData.setValue(authenticationResponse);
                            setActionStatus(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                    }
                });
    }

    public MutableLiveData<AuthenticationResponse> getAuthenticationResponseMutableLiveData() {
        return authenticationResponseMutableLiveData ;
    }

    public Boolean getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Boolean actionStatus) {
        this.actionStatus = actionStatus;
    }
}
