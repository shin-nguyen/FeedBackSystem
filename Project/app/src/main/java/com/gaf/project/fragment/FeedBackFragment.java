package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.gaf.project.model.Admin;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.TypeFeedback;

import java.util.ArrayList;
import java.util.List;

public class FeedBackFragment extends Fragment implements View.OnClickListener{

    private View view;
    private NavController navigation;
    private RecyclerView recyclerViewFeedback;
    private FeedbackAdapter feedBackAdapter;
    private List<Feedback> listFeedBack;
    private Button btnAddFeedBack;

    public FeedBackFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        navigation = Navigation.findNavController(view);

        btnAddFeedBack = view.findViewById(R.id.btn_add_feedback);
        btnAddFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayAddFragment();
            }
        });
    }

    private void DisplayAddFragment() {

        //get
//        LayoutInflater inflater= this.getLayoutInflater();
//        View alertLayout = inflater.inflate(R.layout.add_feedback,null);

        navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment);
    }

    @Override
    public void onClick(View v) {

    }
}