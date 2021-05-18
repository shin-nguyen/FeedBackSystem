package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.ShowDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.AssignmentId;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Module;
import com.gaf.project.model.Question;
import com.gaf.project.model.Trainee;
import com.gaf.project.model.Trainer;
import com.gaf.project.model.TypeFeedback;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.utils.ApiUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAssignmentFragment extends Fragment {

    private View view;
    private String mission;
    private Button btnSave,btnBack;
    private AssignmentService assignmentService;

    public AddAssignmentFragment() {
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_assignment, container, false);

        Assignment assignment= null;

        Bundle bundle = new Bundle();
        mission = getArguments().getString("mission");
        assignment = (Assignment) getArguments().getSerializable("item");

        final Spinner sprModuleName = (Spinner) view.findViewById(R.id.spinner_module_name);
        String[] items_sprMail= new String[]{"sinh@gmail.com", "nguyen@gmail.com", "quyet@gmail.com"};
        ArrayAdapter<String> adapter_sprMail = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items_sprMail);
        sprModuleName.setAdapter(adapter_sprMail);

        final Spinner sprClassName = (Spinner) view.findViewById(R.id.spinner_class_name);
        String[] items_sprClass= new String[]{"sinh@gmail.com", "nguyen@gmail.com", "quyet@gmail.com"};
        ArrayAdapter<String> adapter_sprClass = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items_sprClass);
        sprClassName.setAdapter(adapter_sprClass);

        final Spinner sprTrainerId = (Spinner) view.findViewById(R.id.spinner_trainer_id);
        String[] items_sprTrainer= new String[]{"sinh@gmail.com", "nguyen@gmail.com", "quyet@gmail.com"};
        ArrayAdapter<String> adapter_sprTrainer = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items_sprTrainer);
        sprTrainerId.setAdapter(adapter_sprTrainer);

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
}