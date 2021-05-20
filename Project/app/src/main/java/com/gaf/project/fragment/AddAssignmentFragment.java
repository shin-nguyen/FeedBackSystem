package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.XmlRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.model.Trainer;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.response.TrainerReponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.ClassService;
import com.gaf.project.service.ModuleService;
import com.gaf.project.service.TrainerService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAssignmentFragment extends Fragment {

    private View view;
    private Button btnSave,btnBack;
    private AssignmentService assignmentService;
    private ModuleService moduleService;
    private ClassService classService;
    private TrainerService trainerService;
    List<Module> moduleList;
    List<Class> classList;
    List<Trainer> trainerList;
    public AddAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentService = ApiUtils.getAssignmentService();
        moduleService = ApiUtils.getModuleService();
        classService = ApiUtils.getClassService();
        trainerService = ApiUtils.getTrainerService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_assignment, container, false);

        final Spinner spnModule = (Spinner) view.findViewById(R.id.spinner_module_name);
        Call<ModuleResponse> callModule =  moduleService.loadModuleAdmin();
        callModule.enqueue(new Callback<ModuleResponse>() {
            @Override
            public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    moduleList = response.body().getModules();
                    ArrayAdapter<Module> adapterModule =
                            new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, moduleList);
                    spnModule.setAdapter(adapterModule);
                }
            }
            @Override
            public void onFailure(Call<ModuleResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });



        final Spinner spnClass = (Spinner) view.findViewById(R.id.spinner_class_name);
        Call<ClassResponse> callClass =  classService.loadListClass();
        callClass.enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    classList = response.body().getClasss();
                    ArrayAdapter<Class> adapterClass =
                            new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classList);
                    spnClass.setAdapter(adapterClass);
                }
            }
            @Override
            public void onFailure(Call<ClassResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        final Spinner spnTrainer = (Spinner) view.findViewById(R.id.spinner_trainer_id);
        Call<TrainerReponse> callTrainer =  trainerService.loadListTrainer();
        callTrainer.enqueue(new Callback<TrainerReponse>() {
            @Override
            public void onResponse(Call<TrainerReponse> call, Response<TrainerReponse> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    trainerList = response.body().getTrainers();
                    ArrayAdapter<Trainer> adapterTrainer =
                            new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, trainerList);
                    spnTrainer.setAdapter(adapterTrainer);
                }
            }
            @Override
            public void onFailure(Call<TrainerReponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnBack= view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message);
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }
    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

}