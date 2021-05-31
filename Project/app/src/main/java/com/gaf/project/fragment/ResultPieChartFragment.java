package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.ViewPageAdapter;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.response.AnswerResponse;
import com.gaf.project.service.AnswerService;
import com.gaf.project.utils.ApiUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultPieChartFragment extends Fragment {

    private View view;
    private ViewPager vpPieChart;
    private ViewPageAdapter vpAdapter;

    private AnswerService answerService;
    private List<Answer> answerList;

    public ResultPieChartFragment() {
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
        view = inflater.inflate(R.layout.fragment_result_pie_chart, container, false);

        if(getArguments() != null) {
            Class mClass = (Class) getArguments().getSerializable("class");
            Module module = (Module) getArguments().getSerializable("module");

            Call<AnswerResponse> callAnswer =  answerService.loadListAnswer(mClass.getClassID(), module.getModuleID());

            callAnswer.enqueue(new Callback<AnswerResponse>() {
                @Override
                public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {

                    if (response.isSuccessful()&& response.body()!=null){
                        answerList = response.body().getAnswers();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("listAnswer", (Serializable) answerList);
                        bundle.putString("className", mClass.getClassName());


                        Fragment frag1 = new PieChart1Fragment();
                        Fragment frag2 = new PieChart2Fragment();

                        frag1.setArguments(bundle);
                        frag2.setArguments(bundle);

                        vpPieChart = view.findViewById(R.id.vpPieChart);

                        vpAdapter = new ViewPageAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, frag1, frag2);
                        vpPieChart.setAdapter(vpAdapter);
                    }
                }
                @Override
                public void onFailure(Call<AnswerResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Error");
                }
            });

//            Bundle bundle = new Bundle();
//            bundle.putSerializable("class", mClass);
//            bundle.putSerializable("module", module);
        }

        return view;
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}