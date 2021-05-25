package com.gaf.project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gaf.project.fragment.PieChart1Fragment;
import com.gaf.project.fragment.PieChart2Fragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PieChart1Fragment();
            case 1:
                return new PieChart2Fragment();
        }

        return new PieChart1Fragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
