package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.service.QuestionService;
import com.gaf.project.service.TopicService;
import com.gaf.project.utils.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionQuestionFragment extends Fragment {

    private View view;
    private String mission;
    private QuestionService questionService;
    private TopicService topicService;
    private TextView title, topicName, warningQuestion;
    private EditText questionContent;
    private Spinner sprTopic;
    private RelativeLayout topicLayoutBox;
    private Button btnSave, btnBack;
    private Question question;
    private ArrayAdapter<Topic> topicArrayAdapter;
    private List<Topic> topicList;

    public ActionQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionService = ApiUtils.getQuestionService();
        topicService = ApiUtils.getTopicService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.action_question, container, false);

        Bundle bundle = new Bundle();
        mission = getArguments().getString("mission");

        try {
            question= (Question) getArguments().getSerializable("item");
        }
        catch (Exception ex){

        }

        title = view.findViewById(R.id.txt_title);
        warningQuestion = view.findViewById(R.id.warning_question_content);
        sprTopic = view.findViewById(R.id.spinner_topic_name);
        questionContent = (EditText) view.findViewById(R.id.edt_question_content);
        topicLayoutBox= view.findViewById(R.id.spinner_topic_box);
        topicName= view.findViewById(R.id.txt_topic_name);
        btnBack = view.findViewById(R.id.btn_back);
        btnSave = view.findViewById(R.id.btn_save);

        if(mission.equals(SystemConstant.ADD)) {
            title.setText("Add Question");
            topicName.setVisibility(View.GONE);
        }

        if(mission.equals(SystemConstant.UPDATE)) {

            title.setText("Edit Question");
            topicLayoutBox.setVisibility(View.GONE);
            topicName.setText(String.valueOf(question.getTopic().getTopicID()));
            questionContent.setText(question.getQuestionContent());
        }

        Call<TopicResponse> callTopic = topicService.loadListTopic();
        callTopic.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    topicList = response.body().getTopic();
                    topicArrayAdapter =
                            new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, topicList);
                    sprTopic.setAdapter(topicArrayAdapter);
                };
            }
            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                warningQuestion.setVisibility(View.GONE);

                Topic topic = (Topic) sprTopic.getSelectedItem();
                String qsContent = String.valueOf(questionContent.getText());

                if (qsContent.isEmpty()) {
                    warningQuestion.setVisibility(View.VISIBLE);
                } else {

                    if (mission.equals(SystemConstant.ADD)) {
                        Question newQuestion = new Question(topic, qsContent);

                        Call<Question> call = questionService.create(newQuestion);
                        call.enqueue(new Callback<Question>() {
                            @Override
                            public void onResponse(Call<Question> call, Response<Question> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    showSuccessDialog("Add Success!");
                                    Log.e("Success", "Update Question success");
                                }
                            }

                            @Override
                            public void onFailure(Call<Question> call, Throwable t) {
                                Log.e("Error", t.getLocalizedMessage());
                                showFailDialog("Error");
                            }
                        });
                        Log.e("Success", "Add Question success");
                    }

                    if (mission.equals(SystemConstant.UPDATE)) {

                        question.setQuestionContent(qsContent);

                        Call<Question> callUpdate = questionService.update(question);
                        callUpdate.enqueue(new Callback<Question>() {
                            @Override
                            public void onResponse(Call<Question> call, Response<Question> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    showSuccessDialog("Edit Success!");
                                    Log.e("Success", "Update Question success");
                                }
                            }

                            @Override
                            public void onFailure(Call<Question> call, Throwable t) {
                                Log.e("Error", t.getLocalizedMessage());
                                showFailDialog("Error");
                            }
                        });
                        Log.e("Success", "Update Question success");
                    }
                }
            }
        });

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

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}