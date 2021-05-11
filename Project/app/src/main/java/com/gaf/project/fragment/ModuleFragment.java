package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.ModuleAdapter;
import com.gaf.project.model.Module;
import com.gaf.project.service.ModuleService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModuleFragment extends Fragment {

    private View view;
    private RecyclerView rcvModule;
    private ModuleAdapter adapter;
    private List<Module> moduleList;
    private ModuleService moduleService;

    public ModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_module, container, false);
        rcvModule = view.findViewById(R.id.rcv_module);
        //Set layout manager -> recyclerView Status
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvModule.setLayoutManager(linearLayoutManager);

        adapter = new ModuleAdapter(new ModuleAdapter.IClickItem<Module>() {
            @Override
            public void update(Module item) {
                clickUpdateStatus(view,item);
            }

            @Override
            public void delete(Module item) {
                clickDeleteAbstract(view,item);
            }
        });



        //Set value adapter for Adapter
        moduleList = new ArrayList<>();
        showToast("Error");


        moduleService = ApiUtils.getModuleService();
        moduleService.loadModuleAdmin()
                .enqueue(new Callback<List<Module>>() {
                    @Override
                    public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                        moduleList = response.body();
                        adapter.setData(moduleList);
                        rcvModule.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Module>> call, Throwable t) {
                        showToast("Error");
                    }
                });
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void clickUpdateStatus(View view, Module item) {

    }

    private void clickDeleteAbstract(View view, Module item){
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

}