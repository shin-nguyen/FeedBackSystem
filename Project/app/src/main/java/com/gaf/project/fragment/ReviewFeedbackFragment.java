package com.gaf.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;

public class ReviewFeedbackFragment extends Fragment {

    private View view;
    private String mission;
    Button saveOrButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_feeback, container, false);

        Button backButton = view.findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        saveOrButton = view.findViewById(R.id.btn_save_or_edit);
        TextView title = view.findViewById(R.id.feedback_review_title);

        mission = getArguments().getString("mission");
        // Choose mission to set text view
        if (mission == SystemConstant.ADD){
            title.setText("Review New Feedback");
        }
        else if (mission == SystemConstant.UPDATE){
            title.setText("Review Edit Feedback");
        }
        else if (mission == SystemConstant.DETAIL){
            title.setText("Detail Feedback");
            saveOrButton.setText("Edit");
        }


        saveOrButton.setOnClickListener(v -> showDialog());

        return view;
    }

    public void showDialog() {

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(SystemConstant.ADD);
        //change dialog message using bundle
//        Bundle bundle = new Bundle();
//        bundle.putString("placeholder", "Update success");
//
//        newFragment.setArguments(bundle);
        newFragment.show(ft, "dialog add success");
    }
}
