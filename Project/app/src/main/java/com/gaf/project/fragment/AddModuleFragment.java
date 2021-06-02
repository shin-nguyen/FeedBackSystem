package com.gaf.project.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
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
import com.gaf.project.constant.MessConstant;
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
            try {
                mModuleEdit = (Module) getArguments().getSerializable("mModule");
                if (mModuleEdit != null) {
                    SimpleDateFormat myFra = new SimpleDateFormat("MM/dd/yyyy");

                    idModule = mModuleEdit.getModuleID();
                    mName.setText(mModuleEdit.getModuleName());

                    mStartDate.setText(myFra.format(mModuleEdit.getStartTime()));
                    mEndDate.setText(myFra.format(mModuleEdit.getEndTime()));
                    mFeedbackStartDate.setText(myFra.format(mModuleEdit.getFeedbackStartTime()));
                    mFeedbackEndDate.setText(myFra.format(mModuleEdit.getFeedbackEndTime()));
                }
            }
            catch (Exception ex){
                Log.e("Error",ex.getLocalizedMessage());
            }
        }



        adminViewModel.getmListAdminLiveData().observe(getViewLifecycleOwner(),admins -> {
            adminAdapter =
                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, admins);
            spnAdmin.setAdapter(adminAdapter);

            if (mModuleEdit!=null){
                Integer postionAdminAdapter = adminAdapter.getPosition(mModuleEdit.getAdmin());
                spnAdmin.setSelection(postionAdminAdapter);
            }

        });

        feedBackViewModel.getListFeedBackLiveData().observe(getViewLifecycleOwner(),feedbacks -> {
            feedbackAdapter =
                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, feedbacks);
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
                    showDialog(moduleViewModel.update(mModule),"Edit");
                }
                else if(mission.equals(SystemConstant.ADD)){
                    Module mModule = new Module(admin,name,startDate,endDate,feedbackStartDate,feedbackEndDate,feedback);
                    showDialog(moduleViewModel.add(mModule),"Update");
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

        Date feedbackStartDate = new Date();
        try {
            feedbackStartDate= dfs.parse(mFeedbackStartDate.getText().toString());
            if (feedbackStartDate.compareTo(new Date())<0&&!mission.equals(SystemConstant.UPDATE)){
                mFeedbackStartDateWarning.setVisibility(View.VISIBLE);
                mFeedbackStartDateWarning.setText(MessConstant.DATE_FEEDBACK_START_NOW);
                flag = false;

            }
        } catch (ParseException e) {
            mFeedbackStartDateWarning.setVisibility(View.VISIBLE);
            mFeedbackStartDateWarning.setText(MessConstant.DATE_NULL);
            flag = false;
        }

        try {
            Date date = dfs.parse(mFeedbackEndDate.getText().toString());
            if (date.compareTo(startDate)<0){
                mFeedbackEndDateWarning.setVisibility(View.VISIBLE);
                mFeedbackEndDateWarning.setText(MessConstant.DATE_FEEDBACK_END_NOW);
                flag = false;

            }
        } catch (ParseException e) {
            mFeedbackEndDateWarning.setVisibility(View.VISIBLE);
            mFeedbackEndDateWarning.setText(MessConstant.DATE_NULL);
            flag = false;
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
        mFeedbackEndDateWarning = view.findViewById(R.id.txt_feedback_end_date_warning);

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