package com.gaf.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.model.Assignment;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends Fragment implements View.OnClickListener{

    public AssignmentFragment(){

    }

    private View view;
    private RecyclerView RecyclerViewAssignment;

    private AssignmentAdapter assignmentAdapter;
    private List<Assignment> listAssignment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerViewAssignment = view.findViewById(R.id.rcv_assignment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        RecyclerViewAssignment.setLayoutManager(linearLayoutManager);

        listAssignment = new ArrayList<>();
        listAssignment.add(new Assignment(1,".NET","Class 1","test","Code"));
        listAssignment.add(new Assignment(2,"java","a1","kai","a123"));
        listAssignment.add(new Assignment(3,"java","a1","kai","a123"));

        assignmentAdapter = new AssignmentAdapter();
        assignmentAdapter.setData(listAssignment);

        RecyclerViewAssignment.setAdapter(assignmentAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}