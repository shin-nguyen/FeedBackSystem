package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.model.Enrollment;
import com.gaf.project.model.Trainer;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.TrainerReponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.viewmodel.ClassViewModel;
import com.gaf.project.viewmodel.EnrollmentViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditEnrollmentFragment extends Fragment {
    private Button btnSave, btnBack;
    private Spinner spnClass;
    private EditText traineeId,traineeName;
    private Enrollment enrollment;
    private List<Class> classList;
    private ArrayAdapter<Class> classAdapter;
    private EnrollmentViewModel enrollmentViewModel;
    private ClassViewModel classViewModel;
    private NavController navigation;

    public EditEnrollmentFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enrollmentViewModel = new ViewModelProvider(this).get(EnrollmentViewModel.class);
        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_enrollment, container, false);

        //initialize variables in view
        initView(view);

        //get enrollment from previous fragment
        enrollment = (Enrollment) getArguments().getSerializable("item");
        traineeId.setText(enrollment.getTrainee().getUserName());
        traineeName.setText(enrollment.getTrainee().getName());

        //get list class from view model
        classViewModel.getListClassLiveData().observe(getViewLifecycleOwner(), classes -> {
            classAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classes);
            spnClass.setAdapter(classAdapter);

            int spnPosition = -1;
            spnPosition = classAdapter.getPosition(enrollment.getMClass());
            spnClass.setSelection(spnPosition);
        });

        //set event for save button
        btnSave.setOnClickListener(v->{
            Integer oldClass = enrollment.getMClass().getClassID();
            Integer newClass = ((Class) spnClass.getSelectedItem()).getClassID();

            showDialog(enrollmentViewModel.update(oldClass, newClass, enrollment.getTrainee().getUserName()), "Update");
        });

        btnBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
        return  view;
    }

    private void initView(View view) {
        traineeId = view.findViewById(R.id.txt_trainer_id);
        traineeName = view.findViewById(R.id.txt_trainee_name);

        spnClass = view.findViewById(R.id.spinner_class_name);
        btnSave = view.findViewById(R.id.btn_save);
        btnBack= view.findViewById(R.id.btn_back);
    }

    public void showDialog(MutableLiveData<String> actionStatus, String action){
        actionStatus.observe(getViewLifecycleOwner(),s -> {
            if(s.equals(SystemConstant.SUCCESS)){
                showSuccessDialog(action+" Success!!");
            }else {
                showFailDialog(action+" Fail!!");
            }
        });
    }

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {
                goToView();
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

    public void goToView(){
        navigation = Navigation.findNavController(getView());
        navigation.navigate(R.id.action_editEnrollmentFragment_to_nav_enrollment);
    }
}