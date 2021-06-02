package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Comment;
import com.gaf.project.model.Topic;
import com.gaf.project.model.Trainee;
import com.gaf.project.response.AnswerResponse;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.service.AnswerService;
import com.gaf.project.service.CommentService;
import com.gaf.project.service.TopicService;
import com.gaf.project.service.TraineeService;
import com.gaf.project.service.TrainerService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoFeedbackFragment extends Fragment {
    private RecyclerView rcvAnswerInFeedbackTrainee;
    private TextView moduleName,comment, trainerName,className;
    private Button btnSubmit;
    private TopicTrainningAdapter topicTrainningAdapter;
    private TopicService topicService;
    private AnswerService answerService;
    private CommentService commentService;
    private List<Topic> topicList;
    Assignment assignment =new Assignment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerService = ApiUtils.getAnswerService();
        topicService = ApiUtils.getTopicService();
        commentService = ApiUtils.getCommentService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_do_feedback, container, false);
        initComponents(view);

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

        topicTrainningAdapter = new TopicTrainningAdapter(assignment.getModule(),assignment.getMClass());
        topicList = new ArrayList<>();

        Call<TopicResponse> topicResponseCall = topicService.loadListTopic();
        topicResponseCall.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    topicList = response.body().getTopic();
                    topicTrainningAdapter.setData(topicList);
                }
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        btnSubmit.setOnClickListener(v->{
            String textComment = comment.getText().toString();

            Trainee trainee = SessionManager.getInstance().getTrainee();
            Comment comment = new Comment(assignment.getModule(),trainee,assignment.getMClass(),textComment);

            List<Answer> mList = topicTrainningAdapter.getmListAnswer();

            if (textComment.isEmpty() || mList.size() != assignment.getModule().getFeedback().getQuestions().size()){
                showFailDialog("Please complete your feedback");
                return;
            }

            Call<Comment> commentCall = commentService.save(comment);
            commentCall.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.isSuccessful()&& response.body()!=null){
                        showSuccessDialog("Success");
                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {

                }
            });


            Call<AnswerResponse> answerResponseCall = answerService.addAll(mList);
            answerResponseCall.enqueue(new Callback<AnswerResponse>() {
                @Override
                public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                    Log.e("Success","Add Answer");
                }

                @Override
                public void onFailure(Call<AnswerResponse> call, Throwable t) {

                }
            });
        });
        rcvAnswerInFeedbackTrainee.setAdapter(topicTrainningAdapter);
        return view;
    }

    private void initComponents(View view) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvAnswerInFeedbackTrainee = view.findViewById(R.id.rcv_answer_in_feedback_trainee);
        rcvAnswerInFeedbackTrainee.setLayoutManager(linearLayoutManager);
        moduleName = (TextView)view.findViewById(R.id.txt_module_name);
        className = (TextView)view.findViewById(R.id.txt_class_name);
        trainerName = view.findViewById(R.id.user_name);
        comment = view.findViewById(R.id.txt_comment);

        btnSubmit  = (Button)view.findViewById(R.id.btn_submit);
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }


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