package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaf.project.R;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;

public class ViewCommentFragment extends Fragment {


    private View view;

    public ViewCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_comment, container, false);

        if(getArguments() != null) {
            Class mClass = (Class) getArguments().getSerializable("class");
            Module module = (Module) getArguments().getSerializable("module");


        }

        return view;
    }
}