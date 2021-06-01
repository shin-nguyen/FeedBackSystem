package com.gaf.project.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Module;
import com.gaf.project.viewmodel.AdminViewModel;
import com.gaf.project.viewmodel.FeedBackViewModel;
import com.gaf.project.viewmodel.ModuleViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddModuleFragment extends Fragment {
    private EditText mName;
    private TextView mStartDate,mEndDate;
    private TextView mFeedbackStartDate,mFeedbackEndDate;

    private TextView mNameWarning, mStartDateWaring, mEndDateWarning,mFeedbackStartDateWarning, mFeedbackEndDateWarning;
    private Spinner spnAdmin,spnFeedbackTitle;
    private ImageButton btnStartDate, btnEndDate;
    private ImageButton btnFeedbackStartDate, btnFeedbackEndDate;


    private Button btnSave, btnBack;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private Date planDate;

    private ModuleViewModel moduleViewModel;
    private AdminViewModel adminViewModel;
    private FeedBackViewModel feedBackViewModel;

    private String mission;

    private TextView mTitle;
    Integer idModule;
    private  View view;
    private List<Admin> adminList;
    private List<Feedback> feedbackList;
    private ArrayAdapter<Admin> adminAdapter;
    private ArrayAdapter<Feedback> feedbackAdapter;

    Module mModuleEdit = null;

    public AddModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        feedBackViewModel = new ViewModelProvider(this).get(FeedBackViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        moduleViewModel.initData();
        adminViewModel.initData();
        feedBackViewModel.initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_module, container, false);
        initComponents(view);


        mission = getArguments().getString("mission");
        if(mission.equals(SystemConstant.ADD)){
            mTitle.setText("Add Module");
        }
        if(mission.equals(SystemConstant.UPDATE)){
            mTitle.setText("Edit Module");
        }

        try {
            mModuleEdit = (Module) getArguments().getSerializable("mModule");
            if (mModuleEdit != null) {

                idModule = mModuleEdit.getModuleID();
                mName.setText(mModuleEdit.getModuleName());
                mStartDate.setEnabled(false);
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.getLocalizedMessage());
        }

        adminViewModel.getmListAdminLiveData().observe(getViewLifecycleOwner(),admins -> {
            adminList = admins;
            adminAdapter =
                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, adminList);
            spnAdmin.setAdapter(adminAdapter);

            if (mModuleEdit!=null){
                Integer postionAdminAdapter = adminAdapter.getPosition(mModuleEdit.getAdmin());
                spnAdmin.setSelection(postionAdminAdapter);
            }

        });

        feedBackViewModel.getListFeedBackLiveData().observe(getViewLifecycleOwner(),feedbacks -> {
            feedbackList = feedbacks;
            feedbackAdapter =
                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, feedbackList);
            spnFeedbackTitle.setAdapter(feedbackAdapter);

            if (mModuleEdit!=null){
                Integer postionFeedbackAdapter = feedbackAdapter.getPosition(mModuleEdit.getFeedback());
                spnFeedbackTitle.setSelection(postionFeedbackAdapter);
            }
        });

        btnSave.setOnClickListener(v->{
            Boolean validateFlag = check();

            if(validateFlag){
                String name = mName.getText().toString().trim();

                Date startDate = dateToTextView(mStartDate);
                Date endDate = dateToTextView(mEndDate);
                Date feedbackStartDate = dateToTextView(mFeedbackStartDate);
                Date feedbackEndDate = dateToTextView(mFeedbackEndDate);

                Admin admin = (Admin) spnAdmin.getSelectedItem();
                Feedback feedback = (Feedback) spnFeedbackTitle.getSelectedItem();
                if(mission.equals(SystemConstant.UPDATE)){
                    Module mModule = new Module(idModule,admin,name,startDate,endDate,feedbackStartDate,feedbackEndDate,feedback)   ;
                    moduleViewModel.update(mModule);
                }
                else if(mission.equals(SystemConstant.ADD)){
                    Module mModule = new Module(admin,name,startDate,endDate,feedbackStartDate,feedbackEndDate,feedback)   ;
                    moduleViewModel.add(mModule);
                }
            }
        });

        //Select plan date
        btnStartDate.setOnClickListener(v -> {
            openDate(mStartDate,v);
        });
        //Select plan date
        btnEndDate.setOnClickListener(v -> {
           openDate(mEndDate,v);
        });
        btnFeedbackStartDate.setOnClickListener(v->{
            openDate(mFeedbackStartDate,v);
        });
        btnFeedbackEndDate.setOnClickListener(v->{
            openDate(mFeedbackEndDate,v);
        });

        btnBack.setOnClickListener(vi->{
            getActivity().onBackPressed();
        });


        return  view;
    }

    private Date dateToTextView(TextView mDate) {
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
        mNameWarning.setVisibility(View.GONE);
        mStartDateWaring.setVisibility(View.GONE);
        mEndDateWarning.setVisibility(View.GONE);
        mFeedbackStartDateWarning.setVisibility(View.GONE);
        mFeedbackEndDateWarning.setVisibility(View.GONE);

        Boolean flag = true;

        if(mName.getText().toString().isEmpty()){
            mNameWarning.setVisibility(View.VISIBLE);
            return  !flag;
        }

        if(mStartDate.getText().toString().isEmpty()){
            mStartDateWaring.setVisibility(View.VISIBLE);
            return !flag;
        }

        DateFormat dfs = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = new Date();
        try {
            startDate= dfs.parse(mStartDate.getText().toString());
            if (startDate.compareTo(new Date())<0){
                mStartDateWaring.setVisibility(View.VISIBLE);
                return !flag;
            }
        } catch (ParseException e) {
            mStartDateWaring.setVisibility(View.VISIBLE);
            return !flag;
        }

        if(mEndDate.getText().toString().isEmpty()){
            mEndDateWarning.setVisibility(View.VISIBLE);
            return !flag;
        }

        try {
            Date date = dfs.parse(mEndDate.getText().toString());
            if (date.compareTo(startDate)<0){
                mEndDateWarning.setVisibility(View.VISIBLE);
                return !flag;
            }
        } catch (ParseException e) {
            mEndDateWarning.setVisibility(View.VISIBLE);
            return !flag;
        }


        if(mFeedbackStartDate.getText().toString().isEmpty()){
            mFeedbackStartDateWarning.setVisibility(View.VISIBLE);
            return !flag;
        }


        Date feedbackStartDate = new Date();
        try {
            feedbackStartDate= dfs.parse(mFeedbackStartDate.getText().toString());
            if (feedbackStartDate.compareTo(new Date())<0){
                mFeedbackStartDateWarning.setVisibility(View.VISIBLE);
                return !flag;
            }
        } catch (ParseException e) {
            mFeedbackStartDateWarning.setVisibility(View.VISIBLE);
            return !flag;
        }

        if(mFeedbackEndDate.getText().toString().isEmpty()){
            mFeedbackEndDateWarning.setVisibility(View.VISIBLE);
            return !flag;
        }

        try {
            Date date = dfs.parse(mFeedbackEndDate.getText().toString());
            if (date.compareTo(startDate)<0){
                mFeedbackEndDateWarning.setVisibility(View.VISIBLE);
                return !flag;
            }
        } catch (ParseException e) {
            mFeedbackEndDateWarning.setVisibility(View.VISIBLE);
            return !flag;
        }

        return flag;

    }

    private void openDate(TextView mDate,View v) {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(v.getContext(),
                (datePicker, mYear, mMonth, mDayOfMonth) -> {
                    calendar.set(mYear, mMonth, mDayOfMonth);
                    planDate = calendar.getTime();

                    mDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(planDate));
                }
                , year, month, day);

        datePickerDialog.show();
    }

    private void initComponents(View view) {
        mTitle = view.findViewById(R.id.txt_title);
        mName = view.findViewById(R.id.txt_module_name);


        mNameWarning = view.findViewById(R.id.txt_module_name_warning);
        mStartDateWaring=view.findViewById(R.id.txt_start_date_warning);
        mEndDateWarning=view.findViewById(R.id.txt_end_date_warning);
        mFeedbackStartDateWarning = view.findViewById(R.id.txt_feedback_start_date_warning);
        mFeedbackEndDate = view.findViewById(R.id.txt_feedback_end_date_warning);

        mStartDate = view.findViewById(R.id.txt_start_date);
        mEndDate = view.findViewById(R.id.txt_end_date);
        btnStartDate =(ImageButton) view.findViewById(R.id.btn_add_start_date);
        btnEndDate =(ImageButton) view.findViewById(R.id.btn_add_end_date);

        mFeedbackStartDate = view.findViewById(R.id.txt_feedback_start_date);
        mFeedbackEndDate = view.findViewById(R.id.txt_feedback_end_date);
        btnFeedbackStartDate =(ImageButton) view.findViewById(R.id.btn_add_feedback_start_date);
        btnFeedbackEndDate =(ImageButton) view.findViewById(R.id.btn_add_feedback_end_date);

        spnAdmin = view.findViewById(R.id.spinner_admin_id);
        spnFeedbackTitle = view.findViewById(R.id.spinner_feedbacktitle);

        btnSave  =(Button)view.findViewById(R.id.btn_save_module);
        btnBack  = (Button)view.findViewById(R.id.btn_back);

    }
    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, () -> {
            Navigation.findNavController(view).navigate(R.id.action_addModuleFragment_to_nav_module);
        });
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

}