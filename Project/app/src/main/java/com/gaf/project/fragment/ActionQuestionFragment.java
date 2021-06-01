package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.service.QuestionService;
import com.gaf.project.service.TopicService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.viewmodel.QuestionViewModel;
import com.gaf.project.viewmodel.TopicViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionQuestionFragment extends Fragment {

    private View view;
    private QuestionViewModel questionViewModel;
    private TopicViewModel topicViewModel;
    private Question question;
    private ArrayAdapter<Topic> topicArrayAdapter;
    private TextView title, topicName, warningQuestion;
    private EditText questionContent;
    private Spinner sprTopic;
    private Button btnSave, btnBack;
    private RelativeLayout topicLayoutBox;
    private String mission;

    public ActionQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.action_question, container, false);

        initView();

        mission = getArguments().getString("mission");

        if(mission.equals(SystemConstant.ADD)) {
            title.setText("Add Question");
            topicName.setVisibility(View.GONE);
        }

        if(mission.equals(SystemConstant.UPDATE)) {

            question= (Question) getArguments().getSerializable("item");

            title.setText("Edit Question");
            topicLayoutBox.setVisibility(View.GONE);
            topicName.setText(String.valueOf(question.getTopic().getTopicID()));
            questionContent.setText(question.getQuestionContent());
        }

        topicViewModel.getListTopicLiveData().observe(getViewLifecycleOwner(), new Observer<List<Topic>>() {
            @Override
            public void onChanged(List<Topic> topics) {
                topicArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, topics);
                sprTopic.setAdapter(topicArrayAdapter);
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

                        if(checkQuestion(newQuestion,questionViewModel.getListQuestion())){
                            questionViewModel.addQuestion(newQuestion);
                            showDialog("Add");
                        }else {
                            showFailDialog("Question already exist!!");
                        }
                    }

                    if (mission.equals(SystemConstant.UPDATE)) {
                        question.setQuestionContent(qsContent);

                        if(checkQuestion(question,questionViewModel.getListQuestion())){
                            questionViewModel.updateQuestion(question);
                            showDialog("Edit");
                        }else {
                            showFailDialog("Question already exist!!");
                        }
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

    public void initView(){
        title = view.findViewById(R.id.txt_title);
        warningQuestion = view.findViewById(R.id.warning_question_content);
        sprTopic = view.findViewById(R.id.spinner_topic_name);
        questionContent = (EditText) view.findViewById(R.id.edt_question_content);
        topicLayoutBox= view.findViewById(R.id.spinner_topic_box);
        topicName= view.findViewById(R.id.txt_topic_name);
        btnBack = view.findViewById(R.id.btn_back);
        btnSave = view.findViewById(R.id.btn_save);
    }

    public Boolean checkQuestion(Question question, List<Question> questionList){
        for(Question qs : questionList){
            if(qs.getTopic().equals(question.getTopic()) && qs.getQuestionContent().equals(question.getQuestionContent())){
                return false;
            }
        }
        return true;
    }

    public void showDialog(String action){
        Boolean actionStatus = questionViewModel.getActionStatus().booleanValue();
        if(actionStatus){
            showSuccessDialog(action+" Success!!");
        }else {
            showFailDialog(action+" Fail!!");
        }
    }

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {
                Navigation.findNavController(view).navigate(R.id.action_add_question_fragment_to_nav_question);
            }
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

    public void reloadFragment(){
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}