package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaf.project.R;
import com.gaf.project.adapter.FeedbackAdapter;
import com.gaf.project.model.Feedback;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.utils.ApiUtils;

import java.util.List;

public class TraineeHomeFragment extends Fragment {
    private View view;
    private RecyclerView recyclerViewFeedback;
    private FeedbackAdapter feedBackAdapter;
    private List<Feedback> feedbackList;
    private FeedbackService feedbackService;
    private NavController navigation;
    private Bundle bundle = new Bundle();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackService = ApiUtils.getFeedbackService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_trainee_home, container, false);

        
        return view;
    }
}