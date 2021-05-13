package com.gaf.project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Class;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment {
    private ClassService classService;
    private List<Class> classList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_class, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        classService = ApiUtils.getClassService();

        //Set value adapter for Adapter
        classList = new ArrayList<>();
        Call<ClassResponse> call =  classService.loadListClass(
                "Bearer "+ SystemConstant.authenticationResponse.getJwt());

        call.enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {
                response.body().getClasss();
            }

            @Override
            public void onFailure(Call<ClassResponse> call, Throwable t) {
                Log.e("No",t.getLocalizedMessage());
            }
        });
    }
}