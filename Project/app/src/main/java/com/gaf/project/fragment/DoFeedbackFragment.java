package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.TopicTrainningAdapter;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Trainee;

import java.util.List;

public class DoFeedbackFragment extends Fragment {
    private RecyclerView rcvAnswerInFeedbackTrainee;
    private TextView moduleName,comment, trainerName,className;
    private Button btnSubmit;
    private TopicTrainningAdapter topicTrainningAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_do_feedback, container, false);
        initComponents(view);

        Assignment assignment =new Assignment();
        topicTrainningAdapter = new TopicTrainningAdapter();
//        traineeList = new ArrayList<>();

        try {
            assignment = (Assignment) getArguments().getSerializable("item");
            if (assignment != null) {
                moduleName.setText(assignment.getModule().getModuleName());
                className.setText(assignment.getMClass().getClassName());
                trainerName.setText(assignment.getTrainer().getName());
                Log.e("Success","Get Class Success");
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.getLocalizedMessage());
            showToast("Error");
        }

        btnSubmit.setOnClickListener(v->{
            String textComment = comment.getText().toString();

        });
        return view;
    }

    private void initComponents(View view) {
        rcvAnswerInFeedbackTrainee = view.findViewById(R.id.rcv_answer_in_feedback_trainee);

        moduleName = (TextView)view.findViewById(R.id.txt_module_name);
        className = (TextView)view.findViewById(R.id.txt_class_name);
        trainerName = view.findViewById(R.id.user_name);
        comment = view.findViewById(R.id.txt_comment);

        btnSubmit  = (Button)view.findViewById(R.id.btn_submit);
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}