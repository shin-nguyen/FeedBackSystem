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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.gaf.project.viewmodel.QuestionViewModel;
import com.gaf.project.viewmodel.TopicViewModel;
import com.gaf.project.viewmodel.TypeFeedbackViewModel;

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
    private String mission;
    private TextView title;
    private Spinner spnFeedbackType;
    private EditText feedbackTitle;
    private ArrayAdapter<TypeFeedback> typeFeedbackArrayAdapter;
    private Button  btnBack,btnReview;
    private Integer idFb;
    private TypeFeedbackViewModel typeFeedbackViewModel;
    private TopicViewModel topicViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        typeFeedbackViewModel = new ViewModelProvider(this).get(TypeFeedbackViewModel.class);
        topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
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
                    idFb = mFeedbackEdit.getFeedbackID();
                    Log.e("Success","Get Class Success");
                }
            }
            catch (Exception ex){
                Log.e("Error",ex.getLocalizedMessage());
            }

        }

        //load type feedback using ViewModel
        typeFeedbackViewModel.getListTypeFeedbackLiveData().observe(getViewLifecycleOwner(), new Observer<List<TypeFeedback>>() {
            @Override
            public void onChanged(List<TypeFeedback> typeFeedbacks) {
                typeFeedbackArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, typeFeedbacks);
                spnFeedbackType.setAdapter(typeFeedbackArrayAdapter);
            }
        });

        topicAdapter = new TopicAdapter();
        listTopic = new ArrayList<>();

        //load list topic
        topicViewModel.getListTopicLiveData().observe(getViewLifecycleOwner(), new Observer<List<Topic>>() {
            @Override
            public void onChanged(List<Topic> topics) {
                topicAdapter.setData(topics);
                getTopics(topics);
            }
        });

        btnBack.setOnClickListener(v -> getActivity().onBackPressed());


        btnReview.setOnClickListener(v -> {
            navigation = Navigation.findNavController(view);
            if (feedbackTitle.getText().toString().isEmpty()){
                showToast("Please enter feedback title");
                return;
            }

            if (listTopic.size() == topicAdapter.getmListTopic().size()){
                TypeFeedback typeFeedback = (TypeFeedback) spnFeedbackType.getSelectedItem();
                List<Question> questionList = topicAdapter.getmListQuestion();
                if(mission.equals(SystemConstant.ADD)) {
                    Feedback feedback = new Feedback( feedbackTitle.getText().toString(),typeFeedback,questionList);
                    bundle.putSerializable("feedback", feedback);
                }
                else if (mission.equals(SystemConstant.UPDATE)){
                    Feedback feedback = new Feedback(idFb, feedbackTitle.getText().toString(),typeFeedback,questionList);
                    bundle.putSerializable("feedback", feedback);
                }
                navigation.navigate(R.id.action_add_feedback_fragment_to_review_feedback_fragment, bundle);
            }else {
                showToast("Choose at least one question per topic");
            }
        });

        recyclerTopic = view.findViewById(R.id.rcv_topic_in_feedback);
        recyclerTopic.setAdapter(topicAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerTopic.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void getTopics(List<Topic> listTopic) {
        this.listTopic = listTopic;
    }

    private void initComponents(View view) {
        title = view.findViewById(R.id.txt_title);
        spnFeedbackType = view.findViewById(R.id.spn_feedback_type);
        feedbackTitle = view.findViewById(R.id.feedback_title);
        btnBack = view.findViewById(R.id.btn_back);
        btnReview = view.findViewById(R.id.btn_review_feeback);
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_SHORT   ).show();
    }

}
