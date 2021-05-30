package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaf.project.R;
import com.gaf.project.model.Class;
import com.gaf.project.model.Enrollment;
import com.gaf.project.model.Trainee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DetailEnrollmentFragment extends Fragment {
    private TextView idTrainee,phone,traineeName,address,email;
    private TextView idClass, startTime, endTime, capacity,className;
    private Button  btnBack;

    public DetailEnrollmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_enrollment, container, false);
        initComponents(view);

        Enrollment enrollment = new Enrollment();
        DateFormat dfs = new SimpleDateFormat("dd/MM/yyyy");
        enrollment = (Enrollment) getArguments().getSerializable("item");

        Trainee trainee = enrollment.getTrainee();
        Class mClass = enrollment.getMClass();

        idTrainee.setText(trainee.getUserName());
        phone.setText(trainee.getPhone());
        traineeName.setText(trainee.getName());
        address.setText(trainee.getAddress());
        email.setText(trainee.getEmail());

        idClass.setText(mClass.getClassName());
        startTime.setText(dfs.format(mClass.getStartTime()));
        className.setText(mClass.getClassName());
        endTime.setText(dfs.format(mClass.getEndTime()));
        capacity.setText(mClass.getCapacity().toString());
        btnBack.setOnClickListener(v -> getActivity().onBackPressed());
        return  view;
    }
    private void initComponents(View view) {
        idTrainee = (TextView)view.findViewById(R.id.txt_trainee_id);
        phone = view.findViewById(R.id.txt_trainee_phone);
        traineeName = view.findViewById(R.id.txt_trainee_name);
        address = view.findViewById(R.id.txt_trainee_address);
        email = view.findViewById(R.id.txt_trainee_email);

        idClass = (TextView)view.findViewById(R.id.txt_class_id);

        startTime = (TextView)view.findViewById(R.id.txt_class_start_time);
        className = (TextView)view.findViewById(R.id.txt_class_name);
        endTime = (TextView)view.findViewById(R.id.txt_class_end_time);
        capacity = (TextView)view.findViewById(R.id.txt_class_capicity);

        btnBack  = (Button)view.findViewById(R.id.btn_back);
    }
}