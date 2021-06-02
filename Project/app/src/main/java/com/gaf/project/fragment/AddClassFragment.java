package com.gaf.project.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
<<<<<<< HEAD
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
=======
>>>>>>> parent of d8a46df (fix conflict)

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
<<<<<<< HEAD
import com.gaf.project.viewmodel.ClassViewModel;
=======
import com.gaf.project.response.ClassResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;
>>>>>>> parent of d8a46df (fix conflict)

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddClassFragment extends Fragment {

    private EditText mName, mCapacity;
    private TextView mStartDate,mEndDate, mNameWarning, mCapacityWarning, mStartDateWaring, mEndDateWarning;
    private ImageButton btnStartDate, btnEndDate;
    private Button btnSave, btnBack;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private Date planDate;
    private ClassService classService;
    private String mission;
    private TextView mTitle;
    Integer idClass;

    public AddClassFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classService = ApiUtils.getClassService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_class, parent, false);
        initComponents(view);
        Class mClassEdit = null;

        mission = getArguments().getString("mission");
        if(mission.equals(SystemConstant.ADD)){
            mTitle.setText("Add Class");
        }
        else
        if(mission.equals(SystemConstant.UPDATE)){
            mTitle.setText("Edit Class");
<<<<<<< HEAD
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
=======
        }

        try {
            mClassEdit = (Class) getArguments().getSerializable("mClass");
            if (mClassEdit != null) {
                SimpleDateFormat myFra = new SimpleDateFormat("MM/dd/yyyy");
                idClass = mClassEdit.getClassID();
                mName.setText(mClassEdit.getClassName());
                mCapacity.setText(mClassEdit.getCapacity());
                mStartDate.setText(myFra.format(mClassEdit.getStartTime()));
                mEndDate.setText(myFra.format(mClassEdit.getEndTime()));

                mStartDate.setEnabled(false);
                Log.e("Success","Get Class Success");
>>>>>>> parent of d8a46df (fix conflict)
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.getLocalizedMessage());
        }

        btnSave.setOnClickListener(v->{

            mNameWarning.setVisibility(View.GONE);
            mCapacityWarning.setVisibility(View.GONE);
            mStartDateWaring.setVisibility(View.GONE);
            mEndDateWarning.setVisibility(View.GONE);

<<<<<<< HEAD
                if(mission.equals(SystemConstant.UPDATE)){
                    Class mClass = new Class(idClass,name,capacity,startDate,endDate)   ;
                    showDialog(classViewModel.update(mClass),"Edit");
                }
                else if(mission.equals(SystemConstant.ADD)){
                    Class mClass = new Class(name,capacity,startDate,endDate);
                    showDialog(classViewModel.add(mClass),"Add");
=======
            Boolean validateFlag = true;

            if(mName.getText().toString().isEmpty()){
                mNameWarning.setVisibility(View.VISIBLE);
                validateFlag=false;
            }

            if(mCapacity.getText().toString().isEmpty()){
                mCapacityWarning.setVisibility(View.VISIBLE);
                validateFlag=false;
            }

            if(mStartDate.getText().toString().isEmpty()){
                mStartDateWaring.setVisibility(View.VISIBLE);
                validateFlag=false;
            }

            if(mEndDate.getText().toString().isEmpty()){
                mEndDateWarning.setVisibility(View.VISIBLE);
                validateFlag=false;
            }

            if(validateFlag){

                try{
                    String name = mName.getText().toString().trim();
                    Integer capacity = Integer.valueOf(mCapacity.getText().toString());

                    DateFormat dfs = new SimpleDateFormat("MM/dd/yyyy");
                    Date startDate = new Date();
                    try {
                        startDate = dfs.parse(mStartDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Date endDate = new Date();
                    try {
                        endDate = dfs.parse(mEndDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(mission.equals(SystemConstant.UPDATE)){

                        Class mClass = new Class(idClass,name,capacity,startDate,endDate)   ;
                        Call<Class> call =  classService.update( mClass );
                        call.enqueue(new Callback<Class>() {
                            @Override
                            public void onResponse(Call<Class> call, Response<Class> response) {
                                if (response.isSuccessful()&&response.body()!=null) {
                                    showSuccessDialog("Edit Success!");
                                    Log.e("Success","Update Class success");
                                }
                            }

                            @Override
                            public void onFailure(Call<Class> call, Throwable t) {
                                Log.e("Error",t.getLocalizedMessage());
                                showFailDialog("Error");
                            }
                        });
                        Log.e("Success","Send Class success");
                    }
                    else if(mission.equals(SystemConstant.ADD)){

                        Class mClass = new Class(name,capacity,startDate,endDate);
                        Call<Class> call =  classService.create( mClass );
                        call.enqueue(new Callback<Class>() {
                            @Override
                            public void onResponse(Call<Class> call, Response<Class> response) {
                                if (response.isSuccessful()&&response.body()!=null) {
                                    showSuccessDialog("Add Success!");
                                }
                            }

                            @Override
                            public void onFailure(Call<Class> call, Throwable t) {
                                Log.e("Error",t.getLocalizedMessage());
                                showFailDialog("Error");
                            }
                        });
                    }
                }catch (Exception ex){

>>>>>>> parent of d8a46df (fix conflict)
                }
            }
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

                        mStartDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(planDate));
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

                        mEndDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(planDate));
                    }
                    , year, month, day);

            datePickerDialog.show();
        });

        btnBack.setOnClickListener(vi->{
            getActivity().onBackPressed();
        });

        return view;
    }

<<<<<<< HEAD
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
=======
>>>>>>> parent of d8a46df (fix conflict)
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
<<<<<<< HEAD
    public void showDialog(MutableLiveData<String> actionStatus, String action){
        actionStatus.observe(getViewLifecycleOwner(),s -> {
            if(s.equals(SystemConstant.SUCCESS)){
                showSuccessDialog(action+" Success!!");
            }else {
                showFailDialog(action+" Fail!!");
            }
        });

    }
=======

>>>>>>> parent of d8a46df (fix conflict)
    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {

            }
        });
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }
}