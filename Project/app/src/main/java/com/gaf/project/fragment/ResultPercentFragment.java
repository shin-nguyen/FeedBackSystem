package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.ResultPercentAdapter;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.model.PercentValue;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.AnswerResponse;
import com.gaf.project.response.QuestionResponse;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.service.AnswerService;
import com.gaf.project.service.QuestionService;
import com.gaf.project.service.TopicService;
import com.gaf.project.utils.ApiUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//fragment to show statistics by percent for admin, trainer for nav_result
public class ResultPercentFragment extends Fragment {

    TextView tvTopicName, tvTopicName1, tvTopicName2, tvTopicName3;
    RecyclerView rcvStatisticsTopic, rcvStatisticsTopic1, rcvStatisticsTopic2, rcvStatisticsTopic3;
    private ResultPercentAdapter resultPercentAdapter;
    private List<PercentValue> percentValueList;
    private LinearLayoutManager linearLayoutManager;

    private AnswerService answerService;
    private QuestionService questionService;
    private TopicService topicService;

    private List<Answer> answerList;
    private List<Answer> answerListByQuestion;
    private List<Question> questionList;
    private List<Question> questionListByTopic;
    private List<Topic> topicList;

    private View view;

    public ResultPercentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerService = ApiUtils.getAnswerService();
        questionService = ApiUtils.getQuestionService();
        topicService = ApiUtils.getTopicService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result_percent, container, false);

        tvTopicName = view.findViewById(R.id.tvTopicName);
        tvTopicName1 = view.findViewById(R.id.tvTopicName1);
        tvTopicName2 = view.findViewById(R.id.tvTopicName2);
        tvTopicName3 = view.findViewById(R.id.tvTopicName3);

        rcvStatisticsTopic = view.findViewById(R.id.rcvStatisticsTopic);
        rcvStatisticsTopic1 = view.findViewById(R.id.rcvStatisticsTopic1);
        rcvStatisticsTopic2 = view.findViewById(R.id.rcvStatisticsTopic2);
        rcvStatisticsTopic3 = view.findViewById(R.id.rcvStatisticsTopic3);

        if(getArguments() != null) {
            //receive data
            Class mClass = (Class) getArguments().getSerializable("class");
            Module module = (Module) getArguments().getSerializable("module");

            //load list topic
            topicList = new ArrayList<>();
            Call<TopicResponse> callTopic =  topicService.loadListTopic();
            callTopic.enqueue(new Callback<TopicResponse>() {
                @Override
                public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                        topicList = response.body().getTopic();
                        tvTopicName.setText("I. " + topicList.get(0).getTopicName());
                        tvTopicName1.setText("II. " +topicList.get(1).getTopicName());
                        tvTopicName2.setText("III. " +topicList.get(2).getTopicName());
                        tvTopicName3.setText("IV. " +topicList.get(3).getTopicName());

                        //do step 1 for set up percent statistic
                        doStep1(view, mClass, module);
                    }
                }
                @Override
                public void onFailure(Call<TopicResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Error0");
                }
            });
        }

        return view;
    }

    //step 1 for set up percent statistic: load list answer and do step 2
    private void doStep1(View view, Class mClass, Module module) {

        answerList = new ArrayList<>();
        Call<AnswerResponse> callAnswer =  answerService.loadListAnswer(mClass.getClassID(), module.getModuleID());
        callAnswer.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {

                if (response.isSuccessful()&& response.body()!=null){
                    answerList = response.body().getAnswers();

                    //step 2
                    doStep2(view, answerList);
                }
            }
            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

    }

    //step 2 for set up percent statistic: load list active questions and do final step
    private void doStep2(View view, List<Answer> answerList) {

        questionList = new ArrayList<>();
        Call<QuestionResponse> callAnswer =  questionService.loadListActiveQuestion();
        callAnswer.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {

                if (response.isSuccessful()&& response.body()!=null){
                    questionList = response.body().getQuestions();

                    //do final step: set up percent statistics for topic
                    for(int i=1; i<=4; i++){
                        setupRecyclerView(view, i, answerList, questionList);
                    }
                }
            }
            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

    }

    //set up percent statistic with 2 list answers and list active question
    private void setupRecyclerView(View view, int topicId, List<Answer> answerList, List<Question> questionList) {

        //select list question by topic
        questionListByTopic = new ArrayList<>();
        for(int i=0; i<questionList.size(); i++){
            if(questionList.get(i).getTopic().getTopicID() == topicId){
                questionListByTopic.add(questionList.get(i));
            }
        }

        //create list percent value for adapter
        percentValueList = new ArrayList<>();
        //a question in a topic (topicId)
        for(int i=0; i<questionListByTopic.size(); i++){

            //select all answer of this question
            answerListByQuestion = new ArrayList<>();
            for(int j=0; j<answerList.size(); j++){
                if(answerList.get(j).getQuestion().getQuestionID() == questionListByTopic.get(i).getQuestionID()){
                    answerListByQuestion.add(answerList.get(j));
                }
            }

            //have list answers by question, calculate value
            int valueSum = answerListByQuestion.size();
            //if valueSum = 0
            if(valueSum == 0){
                percentValueList.add(new PercentValue("- " + questionListByTopic.get(i).getQuestionContent(),
                        "0.00%", "0.00%","0.00%","0.00%", "0.00%"));
            }
            //if valueSum != 0
            else {
                List<String> percentValue = new ArrayList<>();

                //percent chart have 5 values: 0,1,2,3,4
                //count by value, calculate the percent
                for (int k = 0; k < 5; k++) {

                    int count = 0;
                    for(int j=0; j<valueSum; j++){
                        if(answerListByQuestion.get(j).getValue() == k){
                            count++;
                        }
                    }

                    int value = count;
                    Double percent = (double)value/(double)valueSum*100;
                    DecimalFormat f = new DecimalFormat("#0.00");
                    percentValue.add(k, f.format(percent)+"%");
                }

                //add list value percent, this list for adapter set data
                //PercentValue is format variable be created for list value percent
                percentValueList.add(new PercentValue("- " + questionListByTopic.get(i).getQuestionContent(),
                        percentValue.get(0), percentValue.get(1), percentValue.get(2), percentValue.get(3), percentValue.get(4)));
            }
        }

        //set up adapter
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        resultPercentAdapter = new ResultPercentAdapter();
        resultPercentAdapter.setData(percentValueList);

        //set up recycler view by topics
        switch (topicId){
            case 1:
                rcvStatisticsTopic.setLayoutManager(linearLayoutManager);
                rcvStatisticsTopic.setAdapter(resultPercentAdapter);
                break;
            case 2:
                rcvStatisticsTopic1.setLayoutManager(linearLayoutManager);
                rcvStatisticsTopic1.setAdapter(resultPercentAdapter);
                break;
            case 3:
                rcvStatisticsTopic2.setLayoutManager(linearLayoutManager);
                rcvStatisticsTopic2.setAdapter(resultPercentAdapter);
                break;
            case 4:
                rcvStatisticsTopic3.setLayoutManager(linearLayoutManager);
                rcvStatisticsTopic3.setAdapter(resultPercentAdapter);
                break;
        }

    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}