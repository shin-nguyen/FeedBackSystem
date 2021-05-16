package com.gaf.project.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.gaf.project.R;
import com.gaf.project.model.Class;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddClassFragment extends Fragment {

    private EditText mName, mId, mCapacity;
    private EditText mStartDate,mEndDate;
    private Button btnStartDate, btnEndDate;
    private Button btnSave, btnBack;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private Date planDate;
    public AddClassFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_class, parent, false);

//        String classId = getArguments().getString("classId","");
        initComponents(view);
//
//        if (classId.isEmpty()){
//
//        }

        btnSave.setOnClickListener(v->{
            String name = mName.getText().toString().trim();
            String capicity = mCapacity.getText().toString().trim();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = new Date();
            try {
                startDate = df.parse(mStartDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date endDate = new Date();
            try {
                endDate = df.parse(mEndDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

           // Class mClass = new Class(name,capicity,);
        });

        //Select plan date
        btnStartDate.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            datePickerDialog = new DatePickerDialog(v.getContext(),
                    (datePicker, mYear, mMonth, mDayOfMonth) -> {
                        calendar.set(mYear, mMonth, mDayOfMonth);
                        planDate = calendar.getTime();

                        mStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(planDate));
                    }
                    , year, month, day);

            datePickerDialog.show();
        });

        //Select plan date
        btnEndDate.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            datePickerDialog = new DatePickerDialog(v.getContext(),
                    (datePicker, mYear, mMonth, mDayOfMonth) -> {
                        calendar.set(mYear, mMonth, mDayOfMonth);
                        planDate = calendar.getTime();

                        mEndDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(planDate));
                    }
                    , year, month, day);

            datePickerDialog.show();
        });


        return view;
    }

    private void initComponents(View view) {
        mCapacity = view.findViewById(R.id.txt_capacity);
        mName = view.findViewById(R.id.txt_class_name);

        mStartDate = view.findViewById(R.id.txt_start_date);
        mEndDate = view.findViewById(R.id.txt_end_date);

        btnSave  =view.findViewById(R.id.btn_save_class);
        btnBack  = view.findViewById(R.id.btn_back);

        btnStartDate = view.findViewById(R.id.btn_add_start_date);
        btnEndDate = view.findViewById(R.id.btn_add_end_date);
    }
}