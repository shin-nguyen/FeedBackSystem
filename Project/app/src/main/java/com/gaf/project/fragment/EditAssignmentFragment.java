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
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.AssignmentId;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.model.Question;
import com.gaf.project.model.Trainer;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAssignmentFragment extends Fragment {

    private View view;
    private String mission;
    private Button btnSave, btnBack;
    private EditText classId, className, moduleId, moduleName;
    private Spinner sprTrainerId;
    private AssignmentService assignmentService;

    public EditAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentService = ApiUtils.getAssignmentService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_assignment, container, false);

        classId = view.findViewById(R.id.txt_class_id);
        className = view.findViewById(R.id.txt_class_name);
        moduleId = view.findViewById(R.id.txt_module_id);
        moduleName = view.findViewById(R.id.txt_module_name);
        sprTrainerId = view.findViewById(R.id.spinner_trainer_id);
        btnSave = view.findViewById(R.id.btn_save);
        btnBack= view.findViewById(R.id.btn_back);

        Assignment assignment= new Assignment();

        Bundle bundle = new Bundle();
        mission = getArguments().getString("mission");
        assignment = (Assignment) getArguments().getSerializable("item");

        Class mClass = assignment.getMClass();
        Module module = assignment.getModule();

        classId.setText(String.valueOf(assignment.getMClass().getClassID()));
        className.setText(String.valueOf(assignment.getMClass().getClassName()));
        moduleId.setText(String.valueOf(assignment.getModule().getModuleID()));
        moduleName.setText(String.valueOf(assignment.getModule().getModuleName()));

        Trainer trainer = new Trainer();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assignment assignment = new Assignment("1234",module,trainer,mClass);

                Call<Assignment> call =  assignmentService.create(assignment);
                call.enqueue(new Callback<Assignment>() {
                    @Override
                    public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                        if (response.isSuccessful()&&response.body()!=null) {
                            showSuccessDialog("Add Success!");
                        }
                    }

                    @Override
                    public void onFailure(Call<Assignment> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                        showFailDialog("Assignment already exist!");
                    }
                });
            }
        });

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
}