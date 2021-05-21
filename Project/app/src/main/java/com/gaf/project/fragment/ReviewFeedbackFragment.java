package com.gaf.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.SuccessDialog;

public class ReviewFeedbackFragment extends Fragment {

    private View view;
    private String mission;
    private String message;
    private NavController navigation;
    Button saveOrEditButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_feedback, container, false);

        Button backButton = view.findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        saveOrEditButton = view.findViewById(R.id.btn_save_or_edit);
        TextView title = view.findViewById(R.id.feedback_review_title);

        mission = getArguments().getString("mission");
        // Choose mission to set text view
        if (mission == SystemConstant.ADD){
            title.setText("Review New Feedback");
            message = "Add Success!";
            saveOrEditButton.setOnClickListener(v -> showDialog(message));
        }
        else if (mission == SystemConstant.UPDATE){
            title.setText("Review Edit Feedback");
            message = "Update Success!";
            saveOrEditButton.setOnClickListener(v -> showDialog(message));
        }
        else if (mission == SystemConstant.DETAIL){
            title.setText("Detail Feedback");
            saveOrEditButton.setText("Edit");
            saveOrEditButton.setOnClickListener(v -> editFeedBack());
        }

        return view;
    }

    private void editFeedBack() {
        Bundle bundle = new Bundle();
        bundle.putString("mission", SystemConstant.UPDATE);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_review_feedback_fragment_to_add_feedback_fragment, bundle);
    }

    public void showDialog(String mission) {

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog dialog = new SuccessDialog(mission);
        //change dialog message using bundle
//        Bundle bundle = new Bundle();
//        bundle.putString("placeholder", "Update success");
//
//        newFragment.setArguments(bundle);
        dialog.show(ft, "dialog add success");
    }
}
