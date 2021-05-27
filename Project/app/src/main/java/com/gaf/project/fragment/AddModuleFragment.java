package com.gaf.project.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Module;
import com.gaf.project.response.AdminResponse;
import com.gaf.project.response.FeedbackResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.AdminService;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.service.ModuleService;
import com.gaf.project.utils.ApiUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddModuleFragment extends Fragment {
    private EditText mName;
    private TextView mStartDate,mEndDate;
    private TextView mFeedbackStartDate,mFeedbackEndDate;

    private TextView mNameWarning, mStartDateWaring, mEndDateWarning;

    private Spinner spnAdmin,spnFeedbackTitle;
    private ImageButton btnStartDate, btnEndDate;
    private ImageButton btnFeedbackStartDate, btnFeedbackEndDate;


    private Button btnSave, btnBack;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private Date planDate;

    private ModuleService moduleService;
    private AdminService adminService;
    private FeedbackService feedbackService;
    private String mission;

    private TextView mTitle;
    Integer idModule;
    private  View view;
    private List<Admin> adminList;
    private List<Feedback> feedbackList;
    private ArrayAdapter<Admin> adminAdapter;
    private ArrayAdapter<Feedback> feedbackAdapter;
    public AddModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleService = ApiUtils.getModuleService();
        adminService = ApiUtils.getAdminService();
        feedbackService = ApiUtils.getFeedbackService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_module, container, false);
        initComponents(view);
        Module mModuleEdit = null;

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
                Log.e("Success","Get Class Success");
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.getLocalizedMessage());
        }


        Call<AdminResponse> callAdmin =  adminService.loadListAdmin();
        new Thread(()-> {
            callAdmin.enqueue(new Callback<AdminResponse>() {
                @Override
                public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                        adminList = response.body().getAdmins();
                        adminAdapter =
                                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, adminList);
                        spnAdmin.setAdapter(adminAdapter);
                    }
                }
                @Override
                public void onFailure(Call<AdminResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Error");
                }
            });}).run();

        Call<FeedbackResponse> callFeedback =  feedbackService.getListFeedback();
        new Thread(()-> {
            callFeedback.enqueue(new Callback<FeedbackResponse>() {
                @Override
                public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                        feedbackList = response.body().getFeedbacks();
                        feedbackAdapter =
                                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, feedbackList);
                        spnFeedbackTitle.setAdapter(feedbackAdapter);
                    }
                }
                @Override
                public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Error");
                }
            });}).run();


        btnSave.setOnClickListener(v->{

            mNameWarning.setVisibility(View.GONE);
            mStartDateWaring.setVisibility(View.GONE);
            mEndDateWarning.setVisibility(View.GONE);

            Boolean validateFlag = true;

            if(mName.getText().toString().isEmpty()){
                mNameWarning.setVisibility(View.VISIBLE);
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
                    Date feedbackStartDate = new Date();
                    try {
                        feedbackStartDate = dfs.parse(mFeedbackStartDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Date feedbackEndDate = new Date();
                    try {
                        feedbackEndDate = dfs.parse(mFeedbackEndDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if(mission.equals(SystemConstant.UPDATE)){

                        Admin admin = (Admin) spnAdmin.getSelectedItem();
                        Feedback feedback = (Feedback) spnFeedbackTitle.getSelectedItem();
                        Module mModule = new Module(idModule,admin,name,startDate,endDate,feedbackStartDate,feedbackEndDate,feedback)   ;
                        Call<Module> call =  moduleService.update( mModule );
                        call.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                if (response.isSuccessful()&&response.body()!=null) {
                                    showSuccessDialog("Edit Success!");
                                    Log.e("Success","Update Class success");
                                }
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.e("Error",t.getLocalizedMessage());
                                showFailDialog("Error");
                            }
                        });
                        Log.e("Success","Send Class success");
                    }
                    else if(mission.equals(SystemConstant.ADD)){
                        Admin admin = (Admin) spnAdmin.getSelectedItem();
                        Feedback feedback = (Feedback) spnFeedbackTitle.getSelectedItem();
                        Module mModule = new Module(admin,name,startDate,endDate,feedbackStartDate,feedbackEndDate,feedback)   ;
                        Call<Module> call =  moduleService.create( mModule );
                        call.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                if (response.isSuccessful()&&response.body()!=null) {
                                    showSuccessDialog("Add Success!");
                                }
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.e("Error",t.getLocalizedMessage());
                                showFailDialog("Error");
                            }
                        });
                    }
                }catch (Exception ex){

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
        SuccessDialog newFragment = new SuccessDialog(message);
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