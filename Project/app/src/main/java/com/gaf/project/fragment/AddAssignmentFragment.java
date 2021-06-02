package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.XmlRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.response.TrainerReponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.ClassService;
import com.gaf.project.service.ModuleService;
import com.gaf.project.service.TrainerService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.viewmodel.AssignmentViewModel;
import com.gaf.project.viewmodel.ClassViewModel;
import com.gaf.project.viewmodel.ModuleViewModel;
import com.gaf.project.viewmodel.QuestionViewModel;
import com.gaf.project.viewmodel.TrainerViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAssignmentFragment extends Fragment {

    private View view;
    private Button btnSave,btnBack;
    private ArrayAdapter<Module> adapterModule;
    private ArrayAdapter<Class> adapterClass;
    private ArrayAdapter<Trainer> adapterTrainer;
    private Boolean flag = true;
    private ClassViewModel classViewModel;
    private ModuleViewModel moduleViewModel;
    private TrainerViewModel trainerViewModel;
    private AssignmentViewModel assignmentViewModel;

    public AddAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);
        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        trainerViewModel = new ViewModelProvider(this).get(TrainerViewModel.class);
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_assignment, container, false);

        //set data to spinner module
        final Spinner spnModule = (Spinner) view.findViewById(R.id.spinner_module_name);
        moduleViewModel.getmListModuleLiveData().observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                adapterModule =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, modules);
                spnModule.setAdapter(adapterModule);
            }
        });

        //set data to spinner class
        final Spinner spnClass = (Spinner) view.findViewById(R.id.spinner_class_name);
        classViewModel.getListClassLiveData().observe(getViewLifecycleOwner(), new Observer<List<Class>>() {
            @Override
            public void onChanged(List<Class> classes) {
                adapterClass =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classes);
                spnClass.setAdapter(adapterClass);
            }
        });

        //set data to spinner trainer
        final Spinner spnTrainer = (Spinner) view.findViewById(R.id.spinner_trainer_id);
        trainerViewModel.getListTrainerLiveData().observe(getViewLifecycleOwner(), new Observer<List<Trainer>>() {
            @Override
            public void onChanged(List<Trainer> trainers) {
                adapterTrainer =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,trainers);
                spnTrainer.setAdapter(adapterTrainer);
            }
        });

        //set event for button save
        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v->{

            //get data from view
            Module module = (Module) spnModule.getSelectedItem();
            Class mClass = (Class) spnClass.getSelectedItem();
            Trainer trainer = (Trainer) spnTrainer.getSelectedItem();

            String code = "";
            if(mClass!=null && module!=null && trainer!=null) {
                code = "CL" + mClass.getClassID() + "M" + module.getModuleID() + "T" + System.currentTimeMillis();

                //create new assignment with data from view
                Assignment newAssignment = new Assignment(code, module, trainer, mClass);

                /*if assignment already exist, show fail dialog
                 * else add assignment and show success dialog*/
                if (assignmentViewModel.getListAssignment() == null) {
                    showDialog("Check your connection!!");
                } else {
                    flag = checkExistAssignment(assignmentViewModel.getListAssignment(), newAssignment);
                    if (flag) {
                        assignmentViewModel.addAssignment(newAssignment);
                        showDialog("Add Assignment");
                    } else {
                        showFailDialog("Assignment already exist!");
                    }
                }
            }else {
                showFailDialog("Check your connection!!");
            }
        });

        //set event for button back - back to previous page
        btnBack= view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
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
    public void showDialog(String action){
        if(assignmentViewModel.getActionStatus()==null){
            showFailDialog("Check your connection!!");
        }else {
            Boolean actionStatus = assignmentViewModel.getActionStatus().booleanValue();
            if(actionStatus){
                showSuccessDialog(action+" Success!!");
            }else {
                showFailDialog(action+" Fail!!");
            }
        }

    }

    //show success dialog
    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {
                Navigation.findNavController(view).navigate(R.id.action_add_assignment_fragment_to_nav_assignment);
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

    //reload page
    public void reloadFragment(){
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}