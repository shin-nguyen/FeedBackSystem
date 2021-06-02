package com.gaf.project.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gaf.project.R;
import com.gaf.project.constant.MessConstant;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Class;
import com.gaf.project.viewmodel.ClassViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddClassFragment extends Fragment {

    private EditText mName, mCapacity;
    private TextView mNameWarning, mCapacityWarning, mStartDateWaring, mEndDateWarning;
    private ImageButton btnStartDate, btnEndDate;
    private EditText  mStartDate,mEndDate;
    private Button btnSave, btnBack;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private Date planDate;
    private ClassViewModel classViewModel;
    private String mission;
    private TextView mTitle;
    Integer idClass;
    private View view;

    public AddClassFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_add_class, parent, false);
        initComponents(view);
        Class mClassEdit = new Class();

        mission = getArguments().getString("mission");
        if(mission.equals(SystemConstant.ADD)){
            mTitle.setText("Add Class");
        }
        else
        if(mission.equals(SystemConstant.UPDATE)){
            mTitle.setText("Edit Class");
            try {
                mClassEdit = (Class) getArguments().getSerializable("mClass");
                if (mClassEdit != null) {
                    SimpleDateFormat myFra = new SimpleDateFormat("MM/dd/yyyy");
                    idClass = mClassEdit.getClassID();
                    mName.setText(mClassEdit.getClassName());
                    mCapacity.setText(mClassEdit.getCapacity().toString());
                    mStartDate.setText(myFra.format(mClassEdit.getStartTime()));
                    mEndDate.setText(myFra.format(mClassEdit.getEndTime()));

                    mStartDate.setEnabled(false);
                    btnStartDate.setEnabled(false);
                }
            }
            catch (Exception ex){
                Log.e("Error",ex.getLocalizedMessage());
            }
        }

        btnSave.setOnClickListener(v->{
            Boolean validateFlag = check();
            if(validateFlag){
                String name = mName.getText().toString().trim();
                Integer capacity = Integer.valueOf(mCapacity.getText().toString());

                Date startDate = dateToEditText(mStartDate);
                Date endDate = dateToEditText(mEndDate);

                if(mission.equals(SystemConstant.UPDATE)){
                    Class mClass = new Class(idClass,name,capacity,startDate,endDate)   ;
                    showDialog(classViewModel.update(mClass),"Edit");
                }
                else if(mission.equals(SystemConstant.ADD)){
                    Class mClass = new Class(name,capacity,startDate,endDate);
                    showDialog(classViewModel.add(mClass),"Add");
                }
            }
        });


        //Select plan date
        btnStartDate.setOnClickListener(v -> {
            setTextDate(mStartDate,v);
        });

        //Select plan date
        btnEndDate.setOnClickListener(v -> {
           setTextDate(mEndDate,v);
        });

        btnBack.setOnClickListener(vi->{
            getActivity().onBackPressed();
        });

        return view;
    }

    private Date dateToEditText(EditText mDate) {
        DateFormat dfs = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        try {
            date = dfs.parse(mDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Boolean check() {
        Boolean flag = true;
        mNameWarning.setVisibility(View.GONE);
        mCapacityWarning.setVisibility(View.GONE);
        mStartDateWaring.setVisibility(View.GONE);
        mEndDateWarning.setVisibility(View.GONE);

        if(mName.getText().toString().isEmpty()||mName.getText().toString().length()>255){
            mNameWarning.setVisibility(View.VISIBLE);
            flag = false;
        }

        if(mCapacity.getText().toString().isEmpty()||Integer.valueOf(mCapacity.getText().toString())<0){
            mCapacityWarning.setVisibility(View.VISIBLE);
            flag = false;
        }

        DateFormat dfs = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = new Date();
        try {
            startDate= dfs.parse(mStartDate.getText().toString());
            if (startDate.compareTo(new Date())<0&&!mission.equals(SystemConstant.UPDATE)){
                mStartDateWaring.setVisibility(View.VISIBLE);
                mStartDateWaring.setText(MessConstant.DATE_NOW);
                flag = false;
            }
        } catch (ParseException e) {
            mStartDateWaring.setVisibility(View.VISIBLE);
            mStartDateWaring.setText(MessConstant.DATE_NULL);
            flag = false;
        }

        try {
            Date date = dfs.parse(mEndDate.getText().toString());
            if (date.compareTo(startDate)<0){
                mEndDateWarning.setVisibility(View.VISIBLE);
                mEndDateWarning.setText(MessConstant.DATE_NOW);
                flag = false;
            }
        } catch (ParseException e) {
            mEndDateWarning.setVisibility(View.VISIBLE);
            mEndDateWarning.setText(MessConstant.DATE_NULL);
            flag = false;
        }

        return  flag;
    }

    private void setTextDate(EditText editText, View v){
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(v.getContext(),
                (datePicker, mYear, mMonth, mDayOfMonth) -> {
                    calendar.set(mYear, mMonth, mDayOfMonth);
                    planDate = calendar.getTime();
                    editText.setText(new SimpleDateFormat("MM/dd/yyyy").format(planDate));
                }
                , year, month, day);

        datePickerDialog.show();
    }
    private void initComponents(View view) {
        mTitle = view.findViewById(R.id.txt_title);
        mCapacity = view.findViewById(R.id.txt_capacity);
        mName = view.findViewById(R.id.txt_class_name);

        mNameWarning = view.findViewById(R.id.txt_class_name_warning);
        mCapacityWarning= view.findViewById(R.id.txt_capacity_warning);
        mStartDateWaring=view.findViewById(R.id.txt_start_date_warning);
        mEndDateWarning=view.findViewById(R.id.txt_end_date_warning);

        mStartDate = view.findViewById(R.id.txt_start_date);
        mEndDate = view.findViewById(R.id.txt_end_date);

        btnSave  =(Button)view.findViewById(R.id.btn_save_class);
        btnBack  = (Button)view.findViewById(R.id.btn_back);

        btnStartDate =(ImageButton) view.findViewById(R.id.btn_add_start_date);
        btnEndDate =(ImageButton) view.findViewById(R.id.btn_add_end_date);
    }
    public void showDialog(MutableLiveData<String> actionStatus, String action){
        actionStatus.observe(getViewLifecycleOwner(),s -> {
            if(s.equals(SystemConstant.SUCCESS)){
                showSuccessDialog(action+" Success!!");
            }else {
                showFailDialog(action+" Fail!!");
            }
        });

    }
    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, () -> {
            Navigation.findNavController(view).navigate(R.id.action_add_class_fragment_to_nav_class);
        });
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }
}