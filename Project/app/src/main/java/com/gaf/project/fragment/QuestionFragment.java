package com.gaf.project.fragment;

import android.os.Bundle;

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
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.adapter.QuestionAdapter;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment implements View.OnClickListener{

    private View view;
    private NavController navigation;
    private RecyclerView recyclerViewQuestion;
    private QuestionAdapter questionAdapter;
    private List<Question> listQuestion;
    private Button btnAddQuestion;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewQuestion = view.findViewById(R.id.rcv_question);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewQuestion.setLayoutManager(linearLayoutManager);

        listQuestion = new ArrayList<>();
        Topic topic = new Topic(1,"Kai");
        Question question = new Question(1,topic,"question",false);
        listQuestion.add(question);

        questionAdapter = new QuestionAdapter();
        questionAdapter.setData(listQuestion,getContext());

        recyclerViewQuestion.setAdapter(questionAdapter);

        navigation = Navigation.findNavController(view);

        btnAddQuestion = view.findViewById(R.id.btn_add_question);
//        btnAddAssignment.setVisibility(View.GONE);//hide button
//        btnAddAssignment.setVisibility(View.VISIBLE);//show button
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation.navigate(R.id.action_nav_question_to_add_question_fragment);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}