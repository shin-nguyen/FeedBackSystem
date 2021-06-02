package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.ClassAdapter;
import com.gaf.project.adapter.TraineeAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Class;
import com.gaf.project.model.Trainee;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailClassFragment extends Fragment {
    private TextView mName, mId;
    private Button  btnBack;
    private RecyclerView rcvTrainee;
    private List<Trainee> traineeList;
    private TraineeAdapter adapter;
    private  View view;
    public DetailClassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.fragment_detail_class, container, false);
        initComponents(view);
        Class mClass =new Class();
        adapter = new TraineeAdapter();

        traineeList = new ArrayList<>();

        btnBack.setOnClickListener(v->{
            getActivity().onBackPressed();
        });

        try {
            mClass = (Class) getArguments().getSerializable("mClass");
            if (mClass != null) {
                mId.setText(mClass.getClassID().toString());
                mName.setText(mClass.getClassName());
                traineeList = (List<Trainee>) mClass.getTrainees();
                Log.e("Success","Get Class Success");
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.getLocalizedMessage());
//            showToast("Error");
        }

        //Set layout manager -> recyclerView Status
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvTrainee = view.findViewById(R.id.rcv_info_class);
        rcvTrainee.setLayoutManager(linearLayoutManager);

        adapter.setData(traineeList);
        rcvTrainee.setAdapter(adapter);
        return  view;
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
    private void initComponents(View view) {
        mId = (TextView)view.findViewById(R.id.txt_class_id);
        mName = (TextView)view.findViewById(R.id.txt_class_name);
        btnBack  = (Button)view.findViewById(R.id.btn_back);
    }
}