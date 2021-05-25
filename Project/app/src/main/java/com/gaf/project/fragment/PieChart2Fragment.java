package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChart2Fragment extends Fragment {

    private PieChartView pieChartView1, pieChartView2, pieChartView3, pieChartView4;

    private View view;

    public PieChart2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pie_chart2, container, false);

        pieChartView1 = view.findViewById(R.id.chart1);
        pieChartView2 = view.findViewById(R.id.chart2);
        pieChartView3 = view.findViewById(R.id.chart3);
        pieChartView4 = view.findViewById(R.id.chart4);

        setupPieChart(view);

        return view;
    }

    private void setupPieChart(View view) {

        List pieData = new ArrayList<>();

        List<String> fbA = new ArrayList<>();
        fbA.add("SD");
        fbA.add("D");
        fbA.add("N");
        fbA.add("A");
        fbA.add("SA");

        int[] noOfFeedback ={6, 20, 6, 5, 0};

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
        pieChartData.setValueLabelBackgroundEnabled(false);
        pieChartData.setValueLabelTextSize(6);
        pieChartView1.setPieChartData(pieChartData);
        pieChartView2.setPieChartData(pieChartData);
        pieChartView3.setPieChartData(pieChartData);
        pieChartView4.setPieChartData(pieChartData);

    }

    BiFunction<String,String,String> getLabel = (String name, String value)->{
        return  name + ": " + value+"%";
    };
}