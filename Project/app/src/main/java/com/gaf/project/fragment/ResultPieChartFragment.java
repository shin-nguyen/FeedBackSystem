package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaf.project.R;
import com.gaf.project.adapter.ViewPageAdapter;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;

public class ResultPieChartFragment extends Fragment {

    private View view;
    private ViewPager vpPieChart;
    private ViewPageAdapter vpAdapter;

    public ResultPieChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result_pie_chart, container, false);

        if(getArguments() != null) {
            Class mClass = (Class) getArguments().getSerializable("class");
            Module module = (Module) getArguments().getSerializable("module");

            Bundle bundle = new Bundle();
            bundle.putSerializable("class", mClass);
            bundle.putSerializable("module", module);

            Fragment frag1 = new PieChart1Fragment();
            Fragment frag2 = new PieChart2Fragment();

            frag1.setArguments(bundle);
            frag2.setArguments(bundle);

            vpPieChart = view.findViewById(R.id.vpPieChart);

            vpAdapter = new ViewPageAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, frag1, frag2);
            vpPieChart.setAdapter(vpAdapter);
        }

        return view;
    }

//    @Override
//    public void onItemSelected(Class c, Module m) {
//        activity.onItemSelected(c, m);
//    }
}