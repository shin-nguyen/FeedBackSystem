package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChart1Fragment extends Fragment {

    private TextView tvClassName;
    private PieChartView pieChartView;

    private View view;

    private String[] feedback = {"SD", "D", "N", "A", "SA"};
    private int[] noOfFeedback ={6, 20, 6, 5, 0};

    public PieChart1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pie_chart1, container, false);

        tvClassName = view.findViewById(R.id.tvClassName);
        pieChartView = view.findViewById(R.id.chart);

        setupPieChart(view);

        return view;
    }

    private void setupPieChart(View view) {

        List pieData = new ArrayList<>();

        //Get list status by User
        List<String> fbA = new ArrayList<>();
        fbA.add("SD");
        fbA.add("D");
        fbA.add("N");
        fbA.add("A");
        fbA.add("SA");

        int fbSum = noOfFeedback[0] + noOfFeedback[1] + noOfFeedback[2] + noOfFeedback[3] + noOfFeedback[4];

        for (int i=0; i<fbA.size(); i++){
            Integer value = noOfFeedback[i];

            if (value==0){
                continue;
            }

            String name  =  fbA.get(i).toString();
            Integer color = SystemConstant.color[(4-i) % SystemConstant.lengthColor];
            Double percent = (double)value/(double)fbSum*100;

            pieData.add(new SliceValue(value, color).setLabel(getLabel.apply(name,String.format("%.1f",percent))));
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setValueLabelTextSize(10);
        pieChartView.setPieChartData(pieChartData);

    }

    BiFunction<String,String,String> getLabel = (String name, String value)->{
        return  name + ": " + value+"%";
    };
}