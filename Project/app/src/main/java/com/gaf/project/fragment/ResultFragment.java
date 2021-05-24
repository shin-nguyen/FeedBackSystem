package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gaf.project.R;

public class ResultFragment extends Fragment {

    private Spinner spnClass, spnModule;
    private LinearLayout layTittle1, layTittle2;
    private TextView tvClassName;
    private Button btnShowOverview, btnViewComment, btnShowDetail;
    private View view;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result, container, false);

        spnClass = view.findViewById(R.id.spnClass);
        spnModule = view.findViewById(R.id.spnModule);
        layTittle1 = view.findViewById(R.id.layTittle1);
        layTittle2 = view.findViewById(R.id.layTittle2);
        tvClassName = view.findViewById(R.id.tvClassName);
        btnShowOverview = view.findViewById(R.id.btnShowOverview);
        btnViewComment = view.findViewById(R.id.btnViewComment);
        btnShowDetail = view.findViewById(R.id.btnShowDetail);

        layTittle1.setVisibility(view.VISIBLE);
        layTittle2.setVisibility(view.GONE);
        btnViewComment.setVisibility(view.GONE);

        Fragment fragPieChart = new ResultPieChartFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.statistics_fragment_container, fragPieChart).commit();

        btnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layTittle1.setVisibility(view.GONE);
                layTittle2.setVisibility(view.VISIBLE);
                btnViewComment.setVisibility(view.VISIBLE);

                Fragment fragPercent = new ResultPercentFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.statistics_fragment_container, fragPercent).commit();

            }
        });

        btnShowOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layTittle1.setVisibility(view.VISIBLE);
                layTittle2.setVisibility(view.GONE);
                btnViewComment.setVisibility(view.GONE);

                Fragment fragPieChart = new ResultPieChartFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.statistics_fragment_container, fragPieChart).commit();
            }
        });

        return view;
    }

}