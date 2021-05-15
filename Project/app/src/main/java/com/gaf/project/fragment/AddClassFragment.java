package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gaf.project.R;
public class AddClassFragment extends Fragment {

    private EditText mName, mId, mCapacity;
    private EditText mStartDate,mEndDate;
    private Button btnStartDate, btnEndDate;
    private Button btnSave, btnBack;

    public AddClassFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_class, parent, false);

        String strtext = getArguments().getString("classId","");
        initComponents(view);

        if (strtext.isEmpty()){

        }

        btnSave.setOnClickListener(v->{
            String name = mName.getText().toString().trim();
            String capicity = mCapacity.getText().toString().trim();

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