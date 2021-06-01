package com.gaf.project.fragment;

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
import com.gaf.project.model.Topic;
import com.gaf.project.response.TopicResponse;
import com.gaf.project.service.TopicService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PieChart2Fragment extends Fragment{

    private TextView tvTopicName1, tvTopicName2, tvTopicName3, tvTopicName4;
    private PieChartView pieChartView1, pieChartView2, pieChartView3, pieChartView4;
    private TopicService topicService;

    private List<Answer> answerList;
    private List<Answer> answerListByTopic;
    private List<Topic> topicList;

    private View view;

    public PieChart2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicService = ApiUtils.getTopicService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pie_chart2, container, false);

        tvTopicName1 = view.findViewById(R.id.tvTopicName);
        tvTopicName2 = view.findViewById(R.id.tvTopicName1);
        tvTopicName3 = view.findViewById(R.id.tvTopicName2);
        tvTopicName4 = view.findViewById(R.id.tvTopicName3);

        pieChartView1 = view.findViewById(R.id.chart1);
        pieChartView2 = view.findViewById(R.id.chart2);
        pieChartView3 = view.findViewById(R.id.chart3);
        pieChartView4 = view.findViewById(R.id.chart4);

        if(getArguments() != null) {
            answerList = new ArrayList<>();
            answerList = (List<Answer>) getArguments().getSerializable("listAnswer");

            topicList = new ArrayList<>();
            Call<TopicResponse> callTopic =  topicService.loadListTopic();
            callTopic.enqueue(new Callback<TopicResponse>() {
                @Override
                public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                        topicList = response.body().getTopic();
                        tvTopicName1.setText(topicList.get(0).getTopicName());
                        tvTopicName2.setText(topicList.get(1).getTopicName());
                        tvTopicName3.setText(topicList.get(2).getTopicName());
                        tvTopicName4.setText(topicList.get(3).getTopicName());

                        for(int i=1; i<=4; i++){
                            setupPieChartTopic(view, answerList, i);
                        }
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

    private void setupPieChartTopic(View view, List<Answer> answerList, int topicId) {

        answerListByTopic = new ArrayList<>();
        for(int i=0; i<answerList.size(); i++){
            if(answerList.get(i).getQuestion().getTopic().getTopicID() == topicId){
                answerListByTopic.add(answerList.get(i));
            }
        }

        doPieChart(view, answerListByTopic, topicId);
    }

    private void doPieChart(View view, List<Answer> li, int topicId) {

        List pieData = new ArrayList<>();

        int answerSum = li.size();
        if(answerSum == 0){
            if(topicId == 1){
                pieChartView1.setVisibility(view.INVISIBLE);
            }
            else if(topicId == 2){
                pieChartView2.setVisibility(view.INVISIBLE);
            }
            else if(topicId == 3){
                pieChartView3.setVisibility(view.INVISIBLE);
            }
            else {
                pieChartView4.setVisibility(view.INVISIBLE);
            }
            return;
        }

        List<String> valueNames = new ArrayList<>();
        valueNames.add("Strongly Disagree");
        valueNames.add("Disagree");
        valueNames.add("Neutral");
        valueNames.add("Agree");
        valueNames.add("Strongly Agree");

        for (int i=0; i<5; i++){
            int count = 0;
            for(int k=0; k<li.size(); k++){
                if(li.get(k).getValue() == i){
                    count++;
                }
            }

            Integer value = count;
            if (value==0){
                continue;
            }

            String name  =  valueNames.get(i);
            Integer color = SystemConstant.color[(4-i) % SystemConstant.lengthColor];
            Double percent = (double)value/(double)answerSum*100;

            pieData.add(new SliceValue(value, color).setLabel(getLabel.apply(name,String.format("%.1f",percent))));
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setValueLabelBackgroundEnabled(false);
        pieChartData.setValueLabelTextSize(6);

        if(topicId == 1){
            pieChartView1.setPieChartData(pieChartData);
        }
        else if(topicId == 2){
            pieChartView2.setPieChartData(pieChartData);
        }
        else if(topicId == 3){
            pieChartView3.setPieChartData(pieChartData);
        }
        else {
            pieChartView4.setPieChartData(pieChartData);
        }
    }

    BiFunction<String,String,String> getLabel = (String name, String value)->{
        return value+"%";
    };

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}