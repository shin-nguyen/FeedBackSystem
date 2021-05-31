package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.ResultPercentAdapter;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.model.PercentValue;
import com.gaf.project.model.Question;
import com.gaf.project.service.AnswerService;
import com.gaf.project.service.QuestionService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultPercentFragment extends Fragment {

    RecyclerView rcvStatisticsTopic, rcvStatisticsTopic1, rcvStatisticsTopic2, rcvStatisticsTopic3;
    private ResultPercentAdapter resultPercentAdapter;
    private List<PercentValue> percentValueList;
    LinearLayoutManager linearLayoutManager;

    private AnswerService answerService;
    private QuestionService questionService;
    private List<Answer> answerList;
    private List<Answer> answerListByQuestion;
    private List<Question> questionListByTopic;

    private View view;

    public ResultPercentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerService = ApiUtils.getAnswerService();
        questionService = ApiUtils.getQuestionService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result_percent, container, false);

        if(getArguments() != null) {
            Class mClass = (Class) getArguments().getSerializable("class");
            Module module = (Module) getArguments().getSerializable("module");

            rcvStatisticsTopic = view.findViewById(R.id.rcvStatisticsTopic);
            rcvStatisticsTopic1 = view.findViewById(R.id.rcvStatisticsTopic1);
            rcvStatisticsTopic2 = view.findViewById(R.id.rcvStatisticsTopic2);
            rcvStatisticsTopic3 = view.findViewById(R.id.rcvStatisticsTopic3);
//            rcvStatisticsTopic21 = view.findViewById(R.id.rcvStatisticsTopic21);

            for(int i=0; i<4; i++){
                setupRecyclerView(view, mClass, module, i);
            }
        }

        return view;
    }

    private void setupRecyclerView(View view,Class c, Module m, int topicId) {

        percentValueList = new ArrayList<>();

//        Call<QuestionResponse> callQuestion =  questionService.loadListQuestionByTopic(topicId);
//        new Thread(()-> {
//            callQuestion.enqueue(new Callback<QuestionResponse>() {
//                @Override
//                public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
//
//                    if (response.isSuccessful()&& response.body()!=null){
//                        questionListByTopic = response.body().getQuestions();
//                    }
//                }
//                @Override
//                public void onFailure(Call<QuestionResponse> call, Throwable t) {
//                    Log.e("Error",t.getLocalizedMessage());
//                    showToast("Error");
//                }
//            });}).run();
//
//        int questionSum = 0;
//        try {
//            questionSum = questionListByTopic.size();
//        }
//        catch (Exception ex) {
//            return;
//        }
//
//        for(int i=0; i<questionListByTopic.size(); i++) {
//
//            Call<AnswerResponse> callAnswer = answerService.loadListAnswerByQuestion(c.getClassID(), m.getModuleID(), questionListByTopic.get(i).getQuestionID());
//            int finalI = i;
//            new Thread(() -> {
//                callAnswer.enqueue(new Callback<AnswerResponse>() {
//                    @Override
//                    public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
//
//                        if (response.isSuccessful() && response.body() != null) {
//                            answerListByQuestion = response.body().getAnswers();
//
//                            int[] valueSum = new int[5];
//                            for(int j=0; j<answerListByQuestion.size(); j++){
//                                int count = 0;
//                                for(int k=0; k<answerList.size(); k++){
//                                    if(answerList.get(k).getValue() == j){
//                                        count++;
//                                    }
//                                }
//                                valueSum[j] = count;
//                            }
//
//                            PercentValue percentValue = new PercentValue("- " + questionListByTopic.get(finalI).getQuestionContent(),
//                                    valueSum[0] + "%", valueSum[1] + "%", valueSum[2] + "%", valueSum[3] + "%", valueSum[4] + "%");
//
//                            percentValueList.add(percentValue);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<AnswerResponse> call, Throwable t) {
//                        Log.e("Error", t.getLocalizedMessage());
//                        showToast("Error");
//                    }
//                });
//            }).run();
//        }

        //int valueSum = percentValueList.size();

        PercentValue percentValue = new PercentValue("- Question content 1...", "6.1%", "20%", "5%", "7%", "0%");
        PercentValue percentValue1 = new PercentValue("- Question content 2...", "6%", "20%", "5%", "7%", "0%");
        PercentValue percentValue2 = new PercentValue("- Question content 3...", "6%", "20%", "5%", "7%", "0%");
        PercentValue percentValue3 = new PercentValue("- Question content 4...", "6%", "20%", "5%", "7%", "0%");
        PercentValue percentValue4 = new PercentValue("- Question content 5...", "6.1%", "20%", "5%", "7%", "0%");
        PercentValue percentValue5 = new PercentValue("- Question content 6...", "6%", "20%", "5%", "7%", "0%");
        PercentValue percentValue6 = new PercentValue("- Question content 7...", "6%", "20%", "5%", "7%", "0%");
        PercentValue percentValue7 = new PercentValue("- Question content 8...", "6%", "20%", "5%", "7%", "0%");

        switch (topicId){
            case 0:
                percentValueList = new ArrayList<>();
                percentValueList.add(percentValue);
                percentValueList.add(percentValue1);
                resultPercentAdapter = new ResultPercentAdapter();
                linearLayoutManager = new LinearLayoutManager(view.getContext());
                rcvStatisticsTopic.setLayoutManager(linearLayoutManager);
                resultPercentAdapter.setData(percentValueList);
                rcvStatisticsTopic.setAdapter(resultPercentAdapter);
            case 1:
                percentValueList = new ArrayList<>();
                percentValueList.add(percentValue2);
                percentValueList.add(percentValue3);
                percentValueList.add(percentValue2);
                percentValueList.add(percentValue3);
                resultPercentAdapter = new ResultPercentAdapter();
                linearLayoutManager = new LinearLayoutManager(view.getContext());
                rcvStatisticsTopic1.setLayoutManager(linearLayoutManager);
                resultPercentAdapter.setData(percentValueList);
                rcvStatisticsTopic1.setAdapter(resultPercentAdapter);
            case 2:
                percentValueList = new ArrayList<>();
                percentValueList.add(percentValue4);
                percentValueList.add(percentValue5);
                resultPercentAdapter = new ResultPercentAdapter();
                linearLayoutManager = new LinearLayoutManager(view.getContext());
                rcvStatisticsTopic2.setLayoutManager(linearLayoutManager);
                resultPercentAdapter.setData(percentValueList);
                rcvStatisticsTopic2.setAdapter(resultPercentAdapter);
            case 3:
                percentValueList = new ArrayList<>();
                percentValueList.add(percentValue6);
                percentValueList.add(percentValue7);
                resultPercentAdapter = new ResultPercentAdapter();
                linearLayoutManager = new LinearLayoutManager(view.getContext());
                rcvStatisticsTopic3.setLayoutManager(linearLayoutManager);
                resultPercentAdapter.setData(percentValueList);
                rcvStatisticsTopic3.setAdapter(resultPercentAdapter);
        }

    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}