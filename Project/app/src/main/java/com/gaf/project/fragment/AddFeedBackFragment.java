package com.gaf.project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.FeedbackAdapter;
import com.gaf.project.adapter.QuestionAdapter;
import com.gaf.project.adapter.TopicAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Module;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.model.TypeFeedback;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.response.TypeFeedbackResponse;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.service.TopicService;
import com.gaf.project.service.TypeFeedbackService;
import com.gaf.project.utils.ApiUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFeedBackFragment extends Fragment {

    private View view;
    private NavController navigation;
    private RecyclerView recyclerTopic;
    private TopicAdapter topicAdapter;
    private List<Topic> listTopic;
    private List<TypeFeedback> typeFeedbacks ;
    private String mission;
    private FeedbackService feedbackService;
    private TopicService topicService;
    private TypeFeedbackService typeFeedbackService;
    private TextView title;
    private Spinner spnFeedbackType;
    private EditText feedbackTitle;
    private ArrayAdapter<TypeFeedback> typeFeedbackAdapter;
    private  Button  btnBack,btnReview;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackService = ApiUtils.getFeedbackService();
        typeFeedbackService = ApiUtils.getTypeFeedbackService();

        topicService = ApiUtils.getTopicService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_feedback, container, false);
        initComponents(view);
        Feedback mFeedbackEdit = null;

        Bundle bundle = new Bundle();
        mission = getArguments().getString("mission");
        // Choose mission to set text view
        if(mission.equals(SystemConstant.ADD)){
            title.setText("Create New Feedback");
            bundle.putString("mission", SystemConstant.ADD);
        }
        else  if(mission.equals(SystemConstant.UPDATE)){
            title.setText("Edit New Feedback");
            bundle.putString("mission", SystemConstant.UPDATE);

            try {
                mFeedbackEdit = (Feedback) getArguments().getSerializable("item");
                if (mFeedbackEdit != null) {

                    Log.e("Success","Get Class Success");
                }
            }
            catch (Exception ex){
                Log.e("Error",ex.getLocalizedMessage());
            }

        }
        Call<TypeFeedbackResponse> callTypeFeedback =  typeFeedbackService.loadListTypeFeedback();
            callTypeFeedback.enqueue(new Callback<TypeFeedbackResponse>() {
            @Override
            public void onResponse(Call<TypeFeedbackResponse> call, Response<TypeFeedbackResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                        typeFeedbacks = response.body().getTypeFeedbacks();
                        typeFeedbackAdapter =
                                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, typeFeedbacks);
                        spnFeedbackType.setAdapter(typeFeedbackAdapter);
                    }
            }
            @Override
            public void onFailure(Call<TypeFeedbackResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        btnBack.setOnClickListener(v -> getActivity().onBackPressed());

        btnReview.setOnClickListener(v -> {
            navigation = Navigation.findNavController(view);
            if (topicAdapter.getmListQuestion().isEmpty()){
                showToast("Error");
            }else {
                if(mission.equals(SystemConstant.ADD)) {
                    TypeFeedback typeFeedback = (TypeFeedback) spnFeedbackType.getSelectedItem();
                    List<Question> questionList = topicAdapter.getmListQuestion();

                    Feedback feedback = new Feedback( feedbackTitle.getText().toString(),typeFeedback,questionList);
                    bundle.putSerializable("feedback", feedback);
                }
                navigation.navigate(R.id.action_add_feedback_fragment_to_review_feedback_fragment, bundle);
            }
        });

        recyclerTopic = view.findViewById(R.id.rcv_topic_in_feedback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerTopic.setLayoutManager(linearLayoutManager);


        topicAdapter = new TopicAdapter();
        listTopic = new ArrayList<>();
        Call<TopicResponse> topicCall = topicService.loadListTopic();
        topicCall.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    listTopic = response.body().getTopic();
                    topicAdapter.setData(listTopic);
                }
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });
        recyclerTopic.setAdapter(topicAdapter);

        return view;
    }

    private void initComponents(View view) {
        title = view.findViewById(R.id.txt_title);
        spnFeedbackType = view.findViewById(R.id.spn_feedback_type);
        feedbackTitle = view.findViewById(R.id.feedback_title);
        btnBack = view.findViewById(R.id.btn_back);
        btnReview = view.findViewById(R.id.btn_review_feeback);
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
