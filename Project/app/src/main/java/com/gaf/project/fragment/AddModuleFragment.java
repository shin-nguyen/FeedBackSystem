package com.gaf.project.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gaf.project.R;
import com.gaf.project.service.ClassService;

import java.util.Calendar;
import java.util.Date;

public class AddModuleFragment extends Fragment {
//    private EditText mName, mCapacity;
//    private TextView mStartDate,mEndDate, mNameWarning, mCapacityWarning, mStartDateWaring, mEndDateWarning;
//    private ImageButton btnStartDate, btnEndDate;
//    private Button btnSave, btnBack;
//    private Calendar calendar;
//    private DatePickerDialog datePickerDialog;
//    private Date planDate;
//    private ClassService classService;
//    private String mission;
//    private TextView mTitle;
//    Integer idClass;
    private  View view;
    public AddModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_module, container, false);
        initComponents(view);
        return  view;
    }

    private void initComponents(View view) {
//        mTitle = view.findViewById(R.id.txt_title);
//        mName = view.findViewById(R.id.txt_capacity);


//        mNameWarning = view.findViewById(R.id.txt_class_name_warning);
//        mCapacityWarning= view.findViewById(R.id.txt_capacity_warning);
//        mStartDateWaring=view.findViewById(R.id.txt_start_date_warning);
//        mEndDateWarning=view.findViewById(R.id.txt_end_date_warning);

//        mStartDate = view.findViewById(R.id.txt_start_date);
//        mEndDate = view.findViewById(R.id.txt_end_date);
//        btnStartDate =(ImageButton) view.findViewById(R.id.btn_add_start_date);
//        btnEndDate =(ImageButton) view.findViewById(R.id.btn_add_end_date);
//
//
//        btnSave  =(Button)view.findViewById(R.id.btn_save_class);
//        btnBack  = (Button)view.findViewById(R.id.btn_back);

    }
}