package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.model.Trainer;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.service.ModuleService;
import com.gaf.project.service.TrainerService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultFragment extends Fragment {

    private Button btnShowOverview, btnViewComment, btnShowDetail;
    private View view;
    private ModuleService moduleService;
    private ClassService classService;
    private List<Module> moduleList;
    private List<Class> classList;
    private ArrayAdapter<Module> adapterModule;
    private ArrayAdapter<Class> adapterClass;
    private  String userRole;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleService = ApiUtils.getModuleService();
        classService = ApiUtils.getClassService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result, container, false);

        btnShowOverview = view.findViewById(R.id.btnShowOverview);
        btnViewComment = view.findViewById(R.id.btnViewComment);
        btnShowDetail = view.findViewById(R.id.btnShowDetail);

        btnViewComment.setVisibility(view.GONE);
        Fragment fragPieChart = new ResultPieChartFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.statistics_fragment_container, fragPieChart).commit();

        userRole = SessionManager.getInstance().getUserRole();

        final Spinner spnClass = (Spinner) view.findViewById(R.id.spinner_class_name);

        if(userRole.equals(SystemConstant.ADMIN_ROLE)){

            Call<ClassResponse> callClass =  classService.loadListClass();
            new Thread(()-> {
                callClass.enqueue(new Callback<ClassResponse>() {
                    @Override
                    public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {

                        if (response.isSuccessful()&& response.body()!=null){
                            classList = response.body().getClasss();
                            adapterClass =
                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classList);
                            spnClass.setAdapter(adapterClass);
                        }
                    }
                    @Override
                    public void onFailure(Call<ClassResponse> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                        showToast("Error");
                    }
                });}).run();
        }
        else if(userRole.equals(SystemConstant.TRAINER_ROLE)){

            Call<ClassResponse> callClass =  classService.loadListClassByTrainer();
            new Thread(()-> {
                callClass.enqueue(new Callback<ClassResponse>() {
                    @Override
                    public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {

                        if (response.isSuccessful()&& response.body()!=null){
                            classList = response.body().getClasss();
                            adapterClass =
                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classList);
                            spnClass.setAdapter(adapterClass);
                        }
                    }
                    @Override
                    public void onFailure(Call<ClassResponse> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                        showToast("Error");
                    }
                });}).run();

        }

        final Spinner spnModule = (Spinner) view.findViewById(R.id.spinner_module_name);

        if(userRole.equals(SystemConstant.ADMIN_ROLE)) {

            Call<ModuleResponse> callModule = moduleService.loadModuleAdmin();
            new Thread(() -> {
                callModule.enqueue(new Callback<ModuleResponse>() {
                    @Override
                    public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            moduleList = response.body().getModules();
                            adapterModule =
                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, moduleList);
                            spnModule.setAdapter(adapterModule);
                        }
                    }

                    @Override
                    public void onFailure(Call<ModuleResponse> call, Throwable t) {
                        Log.e("Error", t.getLocalizedMessage());
                        showToast("Error");
                    }
                });
            }).run();

        }
        else if(userRole.equals(SystemConstant.TRAINER_ROLE)){

            Call<ModuleResponse> callModule = moduleService.loadModuleTrainer();
            new Thread(() -> {
                callModule.enqueue(new Callback<ModuleResponse>() {
                    @Override
                    public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            moduleList = response.body().getModules();
                            adapterModule =
                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, moduleList);
                            spnModule.setAdapter(adapterModule);
                        }
                    }

                    @Override
                    public void onFailure(Call<ModuleResponse> call, Throwable t) {
                        Log.e("Error", t.getLocalizedMessage());
                        showToast("Error");
                    }
                });
            }).run();

        }

        btnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnViewComment.setVisibility(view.VISIBLE);
                Fragment fragPercent = new ResultPercentFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.statistics_fragment_container, fragPercent).commit();

            }
        });

        btnShowOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnViewComment.setVisibility(view.GONE);
                Fragment fragPieChart = new ResultPieChartFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.statistics_fragment_container, fragPieChart).commit();

            }
        });

        btnViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnViewComment.setVisibility(view.GONE);
                Fragment fragViewCmt = new ViewCommentFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.statistics_fragment_container, fragViewCmt).commit();

            }
        });

        return view;
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}