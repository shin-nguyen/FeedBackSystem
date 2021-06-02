package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.model.Trainer;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.TrainerReponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.TrainerService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.viewmodel.AssignmentViewModel;
import com.gaf.project.viewmodel.TrainerViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAssignmentFragment extends Fragment {
    private View view;
    private Button btnSave, btnBack;
    private EditText classId, className, moduleId, moduleName;
    private ArrayAdapter<Trainer> adapterTrainer;
    private Spinner spnTrainer;
    private Assignment assignment;
    private Boolean flag = true;
    private AssignmentViewModel assignmentViewModel;
    private TrainerViewModel trainerViewModel;

    public EditAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        trainerViewModel = new ViewModelProvider(this).get(TrainerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_assignment, container, false);

        //declare view
        initView(view);

        //get assignment from bundle
        assignment = (Assignment) getArguments().getSerializable("item");

        //get information from view
        Class mClass = assignment.getMClass();
        Module module = assignment.getModule();
        Trainer trainer = assignment.getTrainer();

        //set information to view from database
        classId.setText(String.valueOf(mClass.getClassID()));
        className.setText(String.valueOf(mClass.getClassName()));
        moduleId.setText(String.valueOf(module.getModuleID()));
        moduleName.setText(String.valueOf(module.getModuleName()));

        //set data for spinner trainer
        trainerViewModel.getListTrainerLiveData().observe(getViewLifecycleOwner(), new Observer<List<Trainer>>() {
            @Override
            public void onChanged(List<Trainer> trainers) {
                adapterTrainer =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, trainers);
                spnTrainer.setAdapter(adapterTrainer);

                //set default value for spinner trainer
                int spnPosition = -1;
                spnPosition = adapterTrainer.getPosition(trainer);
                spnTrainer.setSelection(spnPosition);
            }
        });

        //set event to button save
        btnSave.setOnClickListener(v->{

            //get trainer name from current assignment
            String trainerName = assignment.getTrainer().getUserName();

            //get trainer from trainer spinner
            Trainer selectedTrainer = (Trainer) spnTrainer.getSelectedItem();

            if(selectedTrainer==null){
                showFailDialog("Check your connection!!");//if trainer is null, show warning
            }else {
                assignment.setTrainer(selectedTrainer); //if trainer not null, set trainer to assignment

                if(assignmentViewModel.getListAssignment()==null){
                    showFailDialog("Check your connection!!"); //check connection to api
                }else{

                    //if assignment already exist, show warning else update it
                    flag = checkExistAssignment(assignmentViewModel.getListAssignment(),assignment);
                    if(flag){
                        showDialog(assignmentViewModel.updateAssignment(trainerName,assignment),"Edit Assignment");
                    }else {
                        showFailDialog("Assignment already exist!");
                    }
                }
            }
        });

        //back to previous page
        btnBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        return view;
    }

    //check the existence of the assignment, return false if the assignment already exists and vice versa
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

    //show dialog when the action is finished
    //show dialog when the action is finished
    public void showDialog(MutableLiveData<String> actionStatus, String action){
        actionStatus.observe(getViewLifecycleOwner(),s -> {
            if(s.equals(SystemConstant.SUCCESS)){
                showSuccessDialog(action+" Success!!");
            }else {
                showFailDialog(action+" Fail!!");
            }
        });

    }

    //show success dialog
    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {
                Navigation.findNavController(view).navigate(R.id.action_edit_assignment_fragment_to_nav_assignment);
            }
        });
        newFragment.show(ft, "dialog success");
    }

    //show fail dialog
    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }

    //show toast
    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

    //declare all view
    private void initView(View view) {
        classId = view.findViewById(R.id.txt_class_id);
        className = view.findViewById(R.id.txt_class_name);
        moduleId = view.findViewById(R.id.txt_module_id);
        moduleName = view.findViewById(R.id.txt_module_name);
        spnTrainer = view.findViewById(R.id.spinner_trainer_id);
        btnSave = view.findViewById(R.id.btn_save);
        btnBack= view.findViewById(R.id.btn_back);
    }
}