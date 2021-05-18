package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Question;

public class AddQuestionFragment extends Fragment {

    private View view;
    private String mission;

    private TextView title, topicName;
    private EditText questionContent;
    private Spinner sprTopic;
    private RelativeLayout topicLayoutBox;
    private Button btnSave, btnBack;

    public AddQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_question, container, false);

        Question question = null;

        Bundle bundle = new Bundle();
        mission = getArguments().getString("mission");
        question= (Question) getArguments().getSerializable("item");

        title = view.findViewById(R.id.txt_title);
        sprTopic = view.findViewById(R.id.spinner_topic_name);
        questionContent = view.findViewById(R.id.edt_question_content);
        topicLayoutBox= view.findViewById(R.id.spinner_topic_box);
        topicName=view.findViewById(R.id.txt_topic_name);
        btnBack = view.findViewById(R.id.btn_back);
        btnSave = view.findViewById(R.id.btn_save);

        if(mission.equals(SystemConstant.ADD)){
            title.setText("Add Question");
            topicName.setVisibility(View.GONE);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSuccessDialog("Add Success!");
                }
            });
        }

        if(mission.equals(SystemConstant.UPDATE)){
            title.setText("Edit Question");
            topicLayoutBox.setVisibility(View.GONE);
            topicName.setText(String.valueOf(question.getTopic().getTopicID()));
            questionContent.setText(question.getQuestionContent());

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSuccessDialog("Edit Success!");
                }
            });
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
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
}