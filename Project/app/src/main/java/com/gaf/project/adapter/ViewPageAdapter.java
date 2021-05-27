package com.gaf.project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gaf.project.fragment.PieChart1Fragment;
import com.gaf.project.fragment.PieChart2Fragment;
import com.gaf.project.model.Module;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    Fragment frag1, frag2;

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior, Fragment frag1, Fragment frag2) {
        super(fm, behavior);
        this.frag1 =frag1;
        this.frag2 =frag2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return frag1;
            case 1:
                return frag2;
        }

        return frag1;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
