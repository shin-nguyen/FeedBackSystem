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
import com.gaf.project.response.AssignmentResponse;
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
    private List<Module> moduleList;
    private List<Class> classList;
    private List<Trainer> trainerList;
    private List<Assignment> assignmentList;
    private ArrayAdapter<Module> adapterModule;
    private ArrayAdapter<Class> adapterClass;
    private ArrayAdapter<Trainer> adapterTrainer;
    private Boolean flag = true;

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

        //set data to spinner module
        final Spinner spnModule = (Spinner) view.findViewById(R.id.spinner_module_name);
        Call<ModuleResponse> callModule =  moduleService.loadModuleAdmin();
        callModule.enqueue(new Callback<ModuleResponse>() {
            @Override
            public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                    moduleList = response.body().getModules();
                    adapterModule =
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

        //set data to spinner class
        final Spinner spnClass = (Spinner) view.findViewById(R.id.spinner_class_name);
        Call<ClassResponse> callClass =  classService.loadListClass();
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
        });

        //set data to spinner trainer
        final Spinner spnTrainer = (Spinner) view.findViewById(R.id.spinner_trainer_id);
        Call<TrainerReponse> callTrainer =  trainerService.loadListTrainer();
        callTrainer.enqueue(new Callback<TrainerReponse>() {
            @Override
            public void onResponse(Call<TrainerReponse> call, Response<TrainerReponse> response) {

                if (response.isSuccessful()&& response.body()!=null){
                    trainerList = response.body().getTrainers();
                    adapterTrainer =
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

<<<<<<< HEAD
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
=======
            assignmentList = new ArrayList<>();
            Call<AssignmentResponse> callListAssignment =  assignmentService.loadListAssignment();
            callListAssignment.enqueue(new Callback<AssignmentResponse>() {
                @Override
                public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                    if (response.isSuccessful()&&response.body()!=null){
                        assignmentList = response.body().getAssignments();
                        flag = checkExistAssignment(assignmentList,newAssignment);
                        if(flag){
                            Call<Assignment> callAddAssignment = assignmentService.create(newAssignment);
                            callAddAssignment(callAddAssignment);
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

            reloadFragment();
>>>>>>> parent of d8a46df (fix conflict)
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

<<<<<<< HEAD
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

=======
    public void callAddAssignment(Call<Assignment> call){
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
                showFailDialog("Error");
            }
        });
>>>>>>> parent of d8a46df (fix conflict)
    }

    //show success dialog
    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {

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