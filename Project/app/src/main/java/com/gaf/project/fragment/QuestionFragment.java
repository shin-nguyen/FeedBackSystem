package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.adapter.QuestionAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.WarningDialog;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.QuestionResponse;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.service.QuestionService;
import com.gaf.project.service.TopicService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionFragment extends Fragment {

    private View view;
    private NavController navigation;
    private QuestionService questionService;
    private TopicService topicService;
    private RecyclerView recyclerViewQuestion;
    private QuestionAdapter questionAdapter;
    private List<Question> listQuestion;
    private Button btnAdd;
    private ArrayAdapter<Topic> topicArrayAdapter;
    private List<Topic> topicList;
    private Spinner sprTopic;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionService = ApiUtils.getQuestionService();
        topicService = ApiUtils.getTopicService();
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
        Call<QuestionResponse> call =  questionService.loadListActiveQuestion();
        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    listQuestion = response.body().getQuestions();
                    questionAdapter.setData(listQuestion);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        sprTopic = view.findViewById(R.id.spinner_topic_name);
        Call<TopicResponse> callTopic = topicService.loadListTopic();
        callTopic.enqueue(new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                new Thread(()-> {
                    if (response.isSuccessful()&& response.body()!=null){
                        topicList= response.body().getTopic();
                        topicArrayAdapter =
                                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, topicList);
                        sprTopic.setAdapter(topicArrayAdapter);
                    }}).run();
            }
            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        questionAdapter = new QuestionAdapter(new QuestionAdapter.IClickItem() {
            @Override
            public void update(Question item) {
                clickUpdate(item);
            }

            @Override
            public void delete(Question item) {
                clickDelete(item);
            }
        });

        recyclerViewQuestion.setAdapter(questionAdapter);

        btnAdd = view.findViewById(R.id.btn_add_question);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mission", SystemConstant.ADD);

                Navigation.findNavController(view).navigate(R.id.action_nav_question_to_add_question_fragment,bundle);
            }
        });
    }

    private void clickUpdate(Question item) {
        Bundle bundle = new Bundle();
        bundle.putString("mission", SystemConstant.UPDATE);
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_question_to_add_question_fragment,bundle);
    }

    private void clickDelete(Question item){

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        final WarningDialog dialog = new WarningDialog(
                () -> {
                    callDeleteQuestion(item);
                },
                "Do you want to delete this Question?");

        dialog.show(ft, "dialog success");
    }

    public void callDeleteQuestion(Question question){
        Call<DeleteResponse> call =  questionService.delete(question.getQuestionID());
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()&&response.body().getDeleted()){
                    showSuccessDialog("Delete success!");
                }
            }
            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                showFailDialog("Delete success!");
                Log.e("Error",t.getLocalizedMessage());
            }
        });

        reloadFragment();
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
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

    public void reloadFragment(){
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}