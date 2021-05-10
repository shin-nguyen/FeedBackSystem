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

public class HomePageFragment extends Fragment implements View.OnClickListener {

    private View view;
    private RecyclerView recyclerViewAssignment;

    private AssignmentAdapter assignmentAdapter;
    private List<Assignment> listAssignment;

    public HomePageFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewAssignment = view.findViewById(R.id.rcv_dashboard);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewAssignment.setLayoutManager(linearLayoutManager);

        listAssignment = new ArrayList<>();
        listAssignment.add(new Assignment(1,1,1,1,"Code"));


        assignmentAdapter = new AssignmentAdapter();
        assignmentAdapter.setData(listAssignment);

        recyclerViewAssignment.setAdapter(assignmentAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}