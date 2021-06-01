package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChart1Fragment extends Fragment {

    private TextView tvClassName;
    private PieChartView pieChartView;
    private View layNote;
    private List<Answer> answerList;

    private View view;

    public PieChart1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pie_chart1, container, false);
        if(getArguments() != null) {

            answerList = (List<Answer>) getArguments().getSerializable("listAnswer");
            String className = getArguments().getString("className");

            tvClassName = view.findViewById(R.id.tvClassName);
            pieChartView = view.findViewById(R.id.chart);
            layNote = view.findViewById(R.id.layNote);

            tvClassName.setText(className);

            setupPieChart(view, answerList);
        }

        return view;
    }

    private void setupPieChart(View view, List<Answer> answerList) {

        List pieData = new ArrayList<>();

        List<String> valueNames = new ArrayList<>();
        valueNames.add("Strongly Disagree");
        valueNames.add("Disagree");
        valueNames.add("Neutral");
        valueNames.add("Agree");
        valueNames.add("Strongly Agree");

        int answerSum = answerList.size();

        for (int i=0; i<5; i++){

            int count = 0;
            for(int j=0; j<answerSum; j++){
                if(answerList.get(j).getValue() == i){
                    count++;
                }
            }

            int value = count;
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
        pieChartData.setValueLabelTextSize(10);
        pieChartView.setPieChartData(pieChartData);
    }

    BiFunction<String,String,String> getLabel = (String name, String value)->{
        return value+"%";
    };

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}