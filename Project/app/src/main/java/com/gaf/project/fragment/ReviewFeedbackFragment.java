package com.gaf.project.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Question;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFeedbackFragment extends Fragment {

    private View view;
    private String mission;
    private String message;
    private NavController navigation;
    Button saveOrEditButton;
    private Feedback feedback;
    private FeedbackService feedbackService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackService = ApiUtils.getFeedbackService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_feedback, container, false);

        Button backButton = view.findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        saveOrEditButton = view.findViewById(R.id.btn_save_or_edit);
        TextView title = view.findViewById(R.id.feedback_review_title);
        TextView feedbackTitle = view.findViewById(R.id.feedback_title);
        TextView adminId = view.findViewById(R.id.txt_admin_id);
        mission = getArguments().getString("mission");
        feedback = (Feedback) getArguments().getSerializable("feedback");
        feedbackTitle.setText(feedback.getTitle());
        String userName = SessionManager.getInstance().getUserName();
        adminId.setText(userName);

        // Choose mission to set text view
        if (mission == SystemConstant.ADD){
            title.setText("Review New Feedback");
            message = "Add Success!";
            saveOrEditButton.setOnClickListener(v->{
                Call<Feedback> feedbackCall = feedbackService.create(feedback);
                feedbackCall.enqueue(new Callback<Feedback>() {
                    @Override
                    public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                        if (response.isSuccessful()&&response.body()!=null) {
                            showSuccessDialog(message);
                            Log.e("Success","Add Feedback success");
                        }
                    }

                    @Override
                    public void onFailure(Call<Feedback> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                        showFailDialog("Error");
                    }
                });
            });
        }
        else if (mission == SystemConstant.UPDATE){
            title.setText("Review Edit Feedback");
            message = "Update Success!";
            saveOrEditButton.setOnClickListener(v->showSuccessDialog(message));
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

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message);
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }
}
