package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Answer1;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.response.AnswerResponse;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.service.AnswerService;
import com.gaf.project.service.ClassService;
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

import static android.content.Context.MODE_PRIVATE;

public class PieChart1Fragment extends Fragment {

    private TextView tvClassName, tvTest;
    private PieChartView pieChartView;

    private AnswerService answerService;
    private List<Answer1> answerList;

    private View view;

    public PieChart1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerService = ApiUtils.getAnswerService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pie_chart1, container, false);
        if(getArguments() != null) {
            Class mClass = (Class) getArguments().getSerializable("class");
            Module module = (Module) getArguments().getSerializable("module");

            test(view, mClass, module);

            tvClassName = view.findViewById(R.id.tvClassName);
            pieChartView = view.findViewById(R.id.chart);

            tvClassName.setText(mClass.getClassName());

            setupPieChart(view, mClass, module);
        }

        return view;
    }

    private void setupPieChart(View view, Class c, Module m) {

        List pieData = new ArrayList<>();

        Call<AnswerResponse> callAnswer =  answerService.loadListAnswer(c.getClassID(), m.getModuleID());
        new Thread(()-> {
            callAnswer.enqueue(new Callback<AnswerResponse>() {
                @Override
                public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                        answerList = response.body().getAnswers();
                    }
                }
                @Override
                public void onFailure(Call<AnswerResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Error");
                }
            });}).run();

        List<String> valueNames = new ArrayList<>();
        valueNames.add("Strongly Disagree");
        valueNames.add("Disagree");
        valueNames.add("Neutral");
        valueNames.add("Agree");
        valueNames.add("Strongly Agree");

        int answerSum;
        try {
            answerSum = answerList.size();
        }
        catch (Exception ex) {
            answerSum = 0;
            return;
        }

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
        pieChartData.setValueLabelTextSize(10);
        pieChartView.setPieChartData(pieChartData);

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