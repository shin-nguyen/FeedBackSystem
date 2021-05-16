package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gaf.project.R;
import com.gaf.project.adapter.FeedbackAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.TypeFeedback;

import java.util.ArrayList;
import java.util.List;

public class FeedBackFragment extends Fragment{

    private View view;
    private NavController navigation;
    private RecyclerView recyclerViewFeedback;
    private FeedbackAdapter feedBackAdapter;
    private List<Feedback> listFeedBack;

    public FeedBackFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);

        recyclerViewFeedback = view.findViewById(R.id.rcv_feedback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewFeedback.setLayoutManager(linearLayoutManager);

        Admin ad = new Admin("user", "hung", "hung@gmail.com", "123");

        listFeedBack = new ArrayList<>();

        TypeFeedback typeFeedback = new TypeFeedback(1,"Ec",false);
        Admin admin = new Admin("thao","thao","thaole","1234");

        listFeedBack.add(new Feedback(1,"Ec",admin,false,typeFeedback,new ArrayList<>()));
        listFeedBack.add(new Feedback(2,"Ec",admin,false,typeFeedback,new ArrayList<>()));
        listFeedBack.add(new Feedback(3,"Ec",admin,false,typeFeedback,new ArrayList<>()));

        feedBackAdapter = new FeedbackAdapter();
        feedBackAdapter.setData(listFeedBack);

        recyclerViewFeedback.setAdapter(feedBackAdapter);

        //open fragment create new feedback by button
        Button btnAddFeedBack = view.findViewById(R.id.btn_add_feedback);
        btnAddFeedBack.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("mission", SystemConstant.ADD);

            navigation = Navigation.findNavController(view);
            navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
        });

        return view;
    }
}