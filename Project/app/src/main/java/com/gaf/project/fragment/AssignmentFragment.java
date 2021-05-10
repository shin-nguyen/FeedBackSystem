package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.MainActivity;
import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.model.Assignment;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends Fragment implements View.OnClickListener{

    private View view;
    private RecyclerView recyclerViewAssignment;
    private AssignmentAdapter assignmentAdapter;
    private List<Assignment> listAssignment;
    private Button btnAddAssignment;

    public AssignmentFragment(){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewAssignment = view.findViewById(R.id.rcv_assignment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewAssignment.setLayoutManager(linearLayoutManager);

        listAssignment = new ArrayList<>();
        listAssignment.add(new Assignment(1,1,1,1,"Code"));
        listAssignment.add(new Assignment(2,2,2,2,"a123"));
        listAssignment.add(new Assignment(3,3,3,3,"a123"));

        assignmentAdapter = new AssignmentAdapter();
        assignmentAdapter.setData(listAssignment);

        recyclerViewAssignment.setAdapter(assignmentAdapter);

        btnAddAssignment = view.findViewById(R.id.btn_add_assignment);
        btnAddAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}