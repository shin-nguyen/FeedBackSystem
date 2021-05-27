package com.gaf.project.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.AnswerResponse;
import com.gaf.project.response.QuestionResponse;
import com.gaf.project.service.AnswerService;
import com.gaf.project.service.QuestionService;
import com.gaf.project.utils.ApiUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class PieChart2Fragment extends Fragment{

    private PieChartView pieChartView1, pieChartView2, pieChartView3, pieChartView4;

    private AnswerService answerService;
    private QuestionService questionService;
    private List<Answer> answerList;
    private List<Answer> answerListByQuestion;
    private List<Question> questionListByTopic;

    private TextView tvTest;

    private View view;

    public PieChart2Fragment() {
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
        view = inflater.inflate(R.layout.fragment_pie_chart2, container, false);
        if(getArguments() != null) {
            Class mClass = (Class) getArguments().getSerializable("class");
            Module module = (Module) getArguments().getSerializable("module");
            test(view, mClass, module);

            pieChartView1 = view.findViewById(R.id.chart1);
            pieChartView2 = view.findViewById(R.id.chart2);
            pieChartView3 = view.findViewById(R.id.chart3);
            pieChartView4 = view.findViewById(R.id.chart4);

            for(int i = 0; i<4; i++){
                setupPieChart(view, mClass, module, i);
            }
        }

        return view;
    }

    private void setupPieChart(View view, Class c, Module m, int topicId) {

        List pieData = new ArrayList<>();

        Call<QuestionResponse> callQuestion =  questionService.loadListQuestionByTopic(topicId);
        new Thread(()-> {
            callQuestion.enqueue(new Callback<QuestionResponse>() {
                @Override
                public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                        questionListByTopic = response.body().getQuestions();
                    }
                }
                @Override
                public void onFailure(Call<QuestionResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Error");
                }
            });}).run();

        int questionSum = 0;
        try {
            questionSum = questionListByTopic.size();
        }
        catch (Exception ex) {
            return;
        }

        for(int i=0; i<questionListByTopic.size(); i++) {

            Call<AnswerResponse> callAnswer = answerService.loadListAnswerByQuestion(c.getClassID(), m.getModuleID(), questionListByTopic.get(i).getQuestionID());
            new Thread(() -> {
                callAnswer.enqueue(new Callback<AnswerResponse>() {
                    @Override
                    public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            answerListByQuestion = response.body().getAnswers();

                            for(int j=0; j<answerListByQuestion.size(); j++) {
                                answerList.add(answerListByQuestion.get(j));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AnswerResponse> call, Throwable t) {
                        Log.e("Error", t.getLocalizedMessage());
                        showToast("Error");
                    }
                });
            }).run();
        }

        int answerSum = 0;
        try {
            answerSum = answerList.size();
        }
        catch (Exception ex) {
            return;
        }

        List<String> valueNames = new ArrayList<>();
        valueNames.add("Strongly Disagree");
        valueNames.add("Disagree");
        valueNames.add("Neutral");
        valueNames.add("Agree");
        valueNames.add("Strongly Agree");

        int[] valueSum = new int[5];
        for(int j=0; j<answerSum; j++){
            int count = 0;
            for(int k=0; k<answerList.size(); k++){
                if(answerList.get(k).getValue() == j){
                    count++;
                }
            }
            valueSum[j] = count;
        }

        for (int i=0; i<valueNames.size(); i++){

            Integer value = valueSum[i];

            if (value==0){
                continue;
            }

            String name  =  valueNames.get(i).toString();
            Integer color = SystemConstant.color[(4-i) % SystemConstant.lengthColor];
            Double percent = (double)value/(double)answerSum*100;

            pieData.add(new SliceValue(value, color).setLabel(getLabel.apply(name,String.format("%.1f",percent))));
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setValueLabelBackgroundEnabled(false);
        pieChartData.setValueLabelTextSize(6);

        switch (topicId){
            case 0:
                pieChartView1.setPieChartData(pieChartData);
            case 1:
                pieChartView2.setPieChartData(pieChartData);
            case 2:
                pieChartView3.setPieChartData(pieChartData);
            case 4:
                pieChartView4.setPieChartData(pieChartData);

        }

    }

    BiFunction<String,String,String> getLabel = (String name, String value)->{
        return  name + ": " + value+"%";
    };

    private void test(View view, Class c, Module m) {
        tvTest = view.findViewById(R.id.tvTest);
        tvTest.setText(c.getClassName()+"\n"+m.getModuleName());
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}