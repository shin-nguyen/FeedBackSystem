package com.gaf.project.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.AssignmentId;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Module;
import com.gaf.project.model.Trainee;
import com.gaf.project.model.Trainer;
import com.gaf.project.model.TypeFeedback;


import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

//        Date nowDate = new Date();
//        Timestamp nowTime = new Timestamp();
//        Collection<Trainee> trainees  = new ArrayList<>();
//        Class  mClass = new Class("1", "2", "Ec", nowDate, nowDate, false, trainees);
//        Admin admin = new Admin("thao","thao","thaole","1234");
//        TypeFeedback typeFeedback = new TypeFeedback(1,"Ec",false);
//        Feedback feedback = new Feedback(1,"Ec",admin,false,typeFeedback,new ArrayList<>());
//        Module module = new Module(1,admin,"Ec",nowDate,nowDate,false,nowTime,nowTime,feedback);
//        Trainer trainer = new Trainer(1,"Ec")
//        AssignmentId  assignmentId = new AssignmentId(mClass,module,);
//        listAssignment.add(new Assignment(1,
//                ));


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