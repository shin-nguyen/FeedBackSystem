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
        listAssignment.add(new Assignment(1,".NET","Class 1","test","Code"));
        listAssignment.add(new Assignment(2,"java","a2","kai","a123"));
        listAssignment.add(new Assignment(3,"java","a3","kai","a123"));

        assignmentAdapter = new AssignmentAdapter();
        assignmentAdapter.setData(listAssignment);

        recyclerViewAssignment.setAdapter(assignmentAdapter);

        btnAddAssignment = view.findViewById(R.id.btn_add_assignment);
        btnAddAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayAddAssignment();
            }
        });
    }

    public void DisplayAddAssignment(){

        //get
        LayoutInflater inflater= this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_assignment,null);

        //
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext(),android.R.style.Theme_Material_Light);
        alert.setView(alertLayout);
        AlertDialog dialog = alert.create();

        alert.setNegativeButton(Html.fromHtml("<font color='#26AC33'>CANCEL</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setPositiveButton(Html.fromHtml("<font color='#26AC33'>ADD</font>"), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //
//        final Spinner sprMail = (Spinner) alertLayout.findViewById(R.id.sprMail);
//        String[] items_sprMail= new String[]{"sinh@gmail.com", "nguyen@gmail.com", "quyet@gmail.com"};
//        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//        //There are multiple variations of this, but this is the basic variant.
//        ArrayAdapter<String> adapter_sprMail = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_sprMail);
//        //set the spinners adapter to the previously created one.
//        sprMail.setAdapter(adapter_sprMail);
//
//        final Spinner sprTimeZone = (Spinner) alertLayout.findViewById(R.id.sprTimeZone);
//        String[] items_sprTimeZone= new String[]{"Pacific Standard Time", "One", "Two"};
//        ArrayAdapter<String> adapter_sprTimeZone = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_sprTimeZone);
//        sprTimeZone.setAdapter(adapter_sprTimeZone);

        //Show dialog
        dialog.show();
    }
}