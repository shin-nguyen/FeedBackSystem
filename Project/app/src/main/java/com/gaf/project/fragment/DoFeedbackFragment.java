package com.gaf.project.fragment;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
<<<<<<< HEAD
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
=======
>>>>>>> parent of d8a46df (fix conflict)
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
<<<<<<< HEAD
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.WarningDialog;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.model.Comment;
import com.gaf.project.model.Topic;
import com.gaf.project.model.Trainee;
import com.gaf.project.utils.SessionManager;
import com.gaf.project.viewmodel.AnswerViewModel;
import com.gaf.project.viewmodel.ClassViewModel;
import com.gaf.project.viewmodel.CommentViewModel;
import com.gaf.project.viewmodel.TopicViewModel;

import java.util.ArrayList;
import java.util.List;

=======
import com.gaf.project.model.Answer;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Trainee;

import java.util.List;

>>>>>>> parent of d8a46df (fix conflict)
public class DoFeedbackFragment extends Fragment {
    private RecyclerView rcvAnswerInFeedbackTrainee;
    private TextView moduleName,comment, trainerName,className;
    private Button btnSubmit;
    private TopicTrainningAdapter topicTrainningAdapter;
<<<<<<< HEAD
    private TopicViewModel topicViewModel;
    private AnswerViewModel answerViewModel;
    private CommentViewModel commentViewModel;
    private List<Topic> topicList;
    private  View view;
    Assignment assignment =new Assignment();
=======

>>>>>>> parent of d8a46df (fix conflict)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        answerViewModel = new ViewModelProvider(this).get(AnswerViewModel.class);
        topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        topicViewModel.initData();
=======
>>>>>>> parent of d8a46df (fix conflict)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =  inflater.inflate(R.layout.fragment_do_feedback, container, false);
        initComponents(view);

        Assignment assignment =new Assignment();
        topicTrainningAdapter = new TopicTrainningAdapter();
//        traineeList = new ArrayList<>();

        try {
            assignment = (Assignment) getArguments().getSerializable("item");
            if (assignment != null) {
                moduleName.setText("Module: " + assignment.getModule().getModuleName());
                className.setText("Class: " + assignment.getMClass().getClassName());
                trainerName.setText(assignment.getTrainer().getName());
                Log.e("Success","Get Class Success");
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.getLocalizedMessage());
            showToast("Error");
        }

<<<<<<< HEAD
        topicTrainningAdapter = new TopicTrainningAdapter(assignment.getModule(),assignment.getMClass());
        topicList = new ArrayList<>();


        topicViewModel.getListTopicLiveData().observe(getViewLifecycleOwner(),topics -> {
                topicList = topics;
                topicTrainningAdapter.setData(topicList);
        });

        btnSubmit.setOnClickListener(v->{
            String textComment = comment.getText().toString();

            List<Answer> mListAnswer = topicTrainningAdapter.getmListAnswer();
            int numberQuestion = assignment.getModule().getFeedback().getQuestions().size() * topicList.size();
            if (textComment.isEmpty() ){
                showFailDialog("Please complete your feedback!");
                return;
            }

            Trainee trainee = SessionManager.getInstance().getTrainee();
            Comment comment = new Comment(assignment.getModule(),trainee,assignment.getMClass(),textComment);

            commentViewModel.add(comment);
            clickSave(mListAnswer);

=======
        btnSubmit.setOnClickListener(v->{
            String textComment = comment.getText().toString();

>>>>>>> parent of d8a46df (fix conflict)
        });
        return view;
    }

    private void clickSave(List<Answer> answers){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        final WarningDialog dialog;
        dialog = new WarningDialog(
                () -> {
                    showDialog( answerViewModel.addAll(answers),"Submit Feedback"); },
                "Do you want to submit Feedback?");

        dialog.show(ft, "dialog success");
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

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {
                Navigation.findNavController(view).navigate(R.id.action_do_feedback_fragment_to_nav_trainee_home_fragment);
            }
        });
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }
=======
>>>>>>> parent of d8a46df (fix conflict)
}