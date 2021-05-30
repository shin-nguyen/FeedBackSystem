package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.model.Trainer;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.TrainerReponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.TrainerService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAssignmentFragment extends Fragment {
    private View view;
    private Button btnSave, btnBack;
    private EditText classId, className, moduleId, moduleName;
    private AssignmentService assignmentService;
    private TrainerService trainerService;
    private List<Trainer> trainerList;
    private List<Assignment> assignmentList;
    private ArrayAdapter<Trainer> adapterTrainer;
    private Spinner spnTrainer;
    private Assignment assignment;
    private Boolean flag = true;

    public EditAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentService = ApiUtils.getAssignmentService();
        trainerService = ApiUtils.getTrainerService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_assignment, container, false);

        initView(view);

        assignment = (Assignment) getArguments().getSerializable("item");

        Class mClass = assignment.getMClass();
        Module module = assignment.getModule();
        Trainer trainer = assignment.getTrainer();

        classId.setText(String.valueOf(mClass.getClassID()));
        className.setText(String.valueOf(mClass.getClassName()));
        moduleId.setText(String.valueOf(module.getModuleID()));
        moduleName.setText(String.valueOf(module.getModuleName()));

        Call<TrainerReponse> callTrainer =  trainerService.loadListTrainer();
        callTrainer.enqueue(new Callback<TrainerReponse>() {
            @Override
            public void onResponse(Call<TrainerReponse> call, Response<TrainerReponse> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    trainerList = response.body().getTrainers();
                    adapterTrainer =
                            new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, trainerList);
                    spnTrainer.setAdapter(adapterTrainer);
                    int spnPosition = -1;
                    spnPosition = adapterTrainer.getPosition(trainer);
                    spnTrainer.setSelection(spnPosition);
                };
            }
            @Override
            public void onFailure(Call<TrainerReponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        btnSave.setOnClickListener(v->{
            String trainerName = assignment.getTrainer().getUserName();

            Trainer selectedTrainer = (Trainer) spnTrainer.getSelectedItem();
            assignment.setTrainer(selectedTrainer);

            assignmentList = new ArrayList<>();
            Call<AssignmentResponse> callListAssignment =  assignmentService.loadListAssignment();
            callListAssignment.enqueue(new Callback<AssignmentResponse>() {
                @Override
                public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                    if (response.isSuccessful()&&response.body()!=null){
                        assignmentList = response.body().getAssignments();
                        flag = checkExistAssignment(assignmentList,assignment);
                        if(flag){
                            Call<Assignment> callAddAssignment = assignmentService.create(assignment);
                            callUpdateAssignment(trainerName);
                        }else {
                            showFailDialog("Assignment already exist!");
                        }
                        Log.e("Success","Assignment get success");
                    }
                }

                @Override
                public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Call API fail!");
                }
            });
        });

        btnBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        return view;
    }

    public Boolean checkExistAssignment(List<Assignment> list, Assignment assignment){
        for(Assignment ass : list){
            if(ass.getTrainer().equals(assignment.getTrainer())
                    && ass.getModule().equals(assignment.getModule())
                    && ass.getMClass().equals(assignment.getMClass())){
                return false;
            }
        }
        return true;
    }

    public void callUpdateAssignment(String trainerName){
        Call<Assignment> call =  assignmentService.update(trainerName,assignment);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    showSuccessDialog("Edit Success!");
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showFailDialog("Assignment already exist!");
            }
        });
    }

    private void initView(View view) {
        classId = view.findViewById(R.id.txt_class_id);
        className = view.findViewById(R.id.txt_class_name);
        moduleId = view.findViewById(R.id.txt_module_id);
        moduleName = view.findViewById(R.id.txt_module_name);
        spnTrainer = view.findViewById(R.id.spinner_trainer_id);
        btnSave = view.findViewById(R.id.btn_save);
        btnBack= view.findViewById(R.id.btn_back);
    }

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {

            }
        });
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