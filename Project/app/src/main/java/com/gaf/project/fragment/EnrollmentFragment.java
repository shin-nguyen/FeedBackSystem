package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gaf.project.R;
import com.gaf.project.adapter.EnrollmentAdapter;
import com.gaf.project.model.Class;
import com.gaf.project.model.Trainee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentFragment extends Fragment {

    private View view;
    private NavController navigation;
    private RecyclerView recyclerViewEnrollment;
    private EnrollmentAdapter EnrollmentAdapter;
    private List<Class> listClass;
    private List<Trainee> listTrainee;

    public EnrollmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_enrollment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewEnrollment = view.findViewById(R.id.rcv_enrollment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewEnrollment.setLayoutManager(linearLayoutManager);

        listClass = new ArrayList<>();
        listTrainee = new ArrayList<>();


        //Class clazz = new Class("1", "class1", "50", LocalDate.now(), LocalDate.now(), false, new ArrayList<>());
        Trainee trainee = new Trainee("hungdo", "hung", "hung@gmail.com", "123", "0123", "Dong Nai", true, 1, "123", "123", new ArrayList<>());

        listTrainee.add(trainee);
        //listClass.add(clazz);

        EnrollmentAdapter enrollmentAdapter = new EnrollmentAdapter();
        enrollmentAdapter.setClassData(listClass);
        enrollmentAdapter.setTraineeData(listTrainee);

        recyclerViewEnrollment.setAdapter(enrollmentAdapter);
    }
}