package com.gaf.project.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.TopicReviewFeedbackAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.service.TopicService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFeedbackFragment extends Fragment {

    private View view;
    private String mission;
    private String message;
    private NavController navigation;
    Button saveOrEditButton;
    private Feedback feedback;
    private FeedbackService feedbackService;
    private Set<Topic> topicSet;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackService = ApiUtils.getFeedbackService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_feedback, container, false);

        Button backButton = view.findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        saveOrEditButton = view.findViewById(R.id.btn_save_or_edit);

        TextView title = view.findViewById(R.id.feedback_review_title);
        TextView feedbackTitle = view.findViewById(R.id.feedback_title);
        TextView adminId = view.findViewById(R.id.txt_admin_id);
        TextView item = view.findViewById(R.id.txt_topic_question);


        mission = getArguments().getString("mission");
        feedback = (Feedback) getArguments().getSerializable("feedback");
        feedbackTitle.setText(feedback.getTitle());

        String userName = SessionManager.getInstance().getUserName();
        adminId.setText(userName);

        topicSet = new HashSet<>();
        for (Question question:feedback.getQuestions()) {
            topicSet.add(question.getTopic());
        }

        SpannableStringBuilder sb = new SpannableStringBuilder("");

        Integer len = 0;
//        topicReviewFeedbackAdapterAdapter.setData(listTopic);
        for (Topic topic: topicSet) {
            String topicName = topic.getTopicName()+"\n";
            sb.append(topicName);
            sb.setSpan(Typeface.BOLD,len,len+topicName.length(),Spannable.SPAN_PRIORITY);
            len+=topicName.length();

            String questionName = "";
            for (Question question: feedback.getQuestions()) {
                if (question.getTopic().getTopicID() == topic.getTopicID()){
                    questionName= questionName + question.getQuestionContent() + "\n";
                }
            }
            questionName= questionName + "\n";

            len+=questionName.length();
            sb.append(questionName);
        }

        item.setText(sb);


        // Choose mission to set text view
        if (mission == SystemConstant.ADD){
            title.setText("Review New Feedback");
            message = "Add Success!";
            //set event for saving or editting
            saveOrEditButton.setOnClickListener(v->{
                Call<Feedback> feedbackCall = feedbackService.create(feedback);
                feedbackCall.enqueue(new Callback<Feedback>() {
                    @Override
                    public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                        if (response.isSuccessful()&&response.body()!=null) {
                            showSuccessDialog(message);
                            Log.e("Success","Add Feedback success");
                        }
                    }

                    @Override
                    public void onFailure(Call<Feedback> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                        showFailDialog("Error");
                    }
                });
            });
        }
        else if (mission == SystemConstant.UPDATE){
            title.setText("Review Edit Feedback");
            message = "Update Success!";
            saveOrEditButton.setOnClickListener(v->showSuccessDialog(message));
        }
        else if (mission == SystemConstant.DETAIL){
            title.setText("Detail Feedback");
            saveOrEditButton.setText("Edit");
            saveOrEditButton.setOnClickListener(v -> editFeedBack());
        }
//        topicReviewFeedbackAdapterAdapter = new TopicReviewFeedbackAdapter(questionList);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
//        recyclerTopicReviewFeedback = view.findViewById(R.id.rcv_topic_in_review_feedback);
//        recyclerTopicReviewFeedback.setLayoutManager(linearLayoutManager);
//        recyclerTopicReviewFeedback.setAdapter(topicReviewFeedbackAdapterAdapter);

        return view;
    }

    private void editFeedBack() {
        Bundle bundle = new Bundle();
        bundle.putString("mission", SystemConstant.UPDATE);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_review_feedback_fragment_to_add_feedback_fragment, bundle);
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
