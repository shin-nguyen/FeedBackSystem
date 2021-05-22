package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gaf.project.R;
import com.gaf.project.adapter.ClassAdapter;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Class;

import java.text.SimpleDateFormat;
import java.util.List;


public class DetailClassFragment extends Fragment {
    private EditText mName, mId;
    private Button  btnBack;
    private TextView mTitle;
    private RecyclerView rcvClass;
    private List<Class> classList;
    private ClassAdapter adapter;

    public DetailClassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_detail_class, container, false);
        initComponents(view);
        Class mClass =null;
        try {
            mClass = (Class) getArguments().getSerializable("mClass");
            if (mClass != null) {
                SimpleDateFormat myFra = new SimpleDateFormat("MM/dd/yyyy");
                mId.setText(mClass.getClassID().toString());
                mName.setText(mClass.getClassName());

                Log.e("Success","Get Class Success");
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.getLocalizedMessage());
        }

        btnBack.setOnClickListener(vi->{
            getActivity().onBackPressed();
        });

        adapter = new ClassAdapter(null,true);
        return  view;
    }

    private void initComponents(View view) {
        mTitle = view.findViewById(R.id.txt_title);
        mId = view.findViewById(R.id.txt_class_id);
        mName = view.findViewById(R.id.txt_class_name);
        btnBack  = (Button)view.findViewById(R.id.btn_back);
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