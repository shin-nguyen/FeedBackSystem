package com.gaf.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class AddFeedBackFragment extends Fragment {

    private View view;
    private NavController navigation;
    private RecyclerView recyclerTopic;
    private TopicAdapter topicAdapter;
    private List<Topic> listTopic;
    private String mission;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_feedback, container, false);

        TextView title = view.findViewById(R.id.txt_feebback);

        Bundle bundle = new Bundle();
        mission = getArguments().getString("mission");
        // Choose mission to set text view
        if (mission == SystemConstant.ADD){
            title.setText("Create New Feedback");
            bundle.putString("mission", SystemConstant.ADD);
        }
        else if (mission == SystemConstant.UPDATE){
            title.setText("Edit New Feedback");
            bundle.putString("mission", SystemConstant.UPDATE);
        }

        Button backButton = view.findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        Button reviewButton = view.findViewById(R.id.btn_review_feeback);
        reviewButton.setOnClickListener(v -> {


//            Bundle bundle = new Bundle();
//            bundle.putString("mission", SystemConstant.UPDATE);

            navigation = Navigation.findNavController(view);
            navigation.navigate(R.id.action_add_feedback_fragment_to_review_feedback_fragment, bundle);
        });

        //
        Topic topic1 = new Topic(1, "topic1");
        Topic topic2 = new Topic(2, "topic2");

        listTopic = new ArrayList<>();
        listTopic.add(topic1);
        listTopic.add(topic2);


        recyclerTopic = view.findViewById(R.id.rcv_topic_in_feedback);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(view.getContext());
        recyclerTopic.setLayoutManager(linearLayoutManager2);

        topicAdapter = new TopicAdapter();
        topicAdapter.setData(listTopic);

        recyclerTopic.setAdapter(topicAdapter);

        return view;
    }


}
