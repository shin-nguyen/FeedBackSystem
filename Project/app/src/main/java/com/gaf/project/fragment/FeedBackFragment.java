package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.FeedbackAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.YesNoDialog;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.TypeFeedback;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.FeedbackResponse;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackFragment extends Fragment{

    private View view;
    private NavController navigation;
    private RecyclerView recyclerViewFeedback;
    private FeedbackAdapter feedBackAdapter;
    private List<Feedback> feedbackList;
    private FeedbackService feedbackService;

    private Bundle bundle = new Bundle();

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackService = ApiUtils.getFeedbackService();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);

        String userRole = SessionManager.getInstance().getUserRole();

        if(userRole.equals(SystemConstant.ADMIN_ROLE)){
            feedbackList = new ArrayList<>();
            Call<FeedbackResponse> call = feedbackService.loadListFeedback();

            call.enqueue(new Callback<FeedbackResponse>() {
                @Override
                public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                    if (response.isSuccessful() && response.body() != null){
                        feedbackList = response.body().getFeedbacks();
                        feedBackAdapter.setData(feedbackList);
                    }
                }

                @Override
                public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                    Log.e("Fail to call api for feedback", t.getLocalizedMessage());
                    showToast("Fail to call api for feedback");
                }
            });
        }else if (userRole.equals(SystemConstant.TRAINER_ROLE)){

            //do something with trainer

        }else if (userRole.equals(SystemConstant.TRAINEE_ROLE)){

            //do something with trainee

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewFeedback = view.findViewById(R.id.rcv_feedback);
        recyclerViewFeedback.setLayoutManager(linearLayoutManager);

        feedBackAdapter = new FeedbackAdapter(new FeedbackAdapter.IClickItem() {
            @Override
            public void detail(Feedback item) {
                clickDetail();
            }

            @Override
            public void update(Feedback item) {
                clickUpdate();
            }

            @Override
            public void delete(Feedback item) {
                clickDelete(item);
            }
        });

//        Admin ad = new Admin("user", "hung", "hung@gmail.com", "123");
//
//        listFeedBack = new ArrayList<>();
//
//        TypeFeedback typeFeedback = new TypeFeedback(1,"Ec",false);
//        Admin admin = new Admin("thao","thao","thaole","1234");
//
//        listFeedBack.add(new Feedback(1,"Ec",admin,false,typeFeedback,new ArrayList<>()));
//        listFeedBack.add(new Feedback(2,"Ec",admin,false,typeFeedback,new ArrayList<>()));
//        listFeedBack.add(new Feedback(3,"Ec",admin,false,typeFeedback,new ArrayList<>()));
        
//        feedBackAdapter.setData(listFeedBack);

        recyclerViewFeedback.setAdapter(feedBackAdapter);

        //open fragment create new feedback by button
        Button btnAddFeedBack = view.findViewById(R.id.btn_add_feedback);
        btnAddFeedBack.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("mission", SystemConstant.ADD);
//
//            navigation = Navigation.findNavController(view);
//            navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
            clickAdd();
        });

        return view;
    }

    private void clickAdd() {
        bundle.putString("mission", SystemConstant.ADD);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
    }

    private void clickUpdate() {
        bundle.putString("mission", SystemConstant.UPDATE);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
    }

    private void clickDelete(Feedback item) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        final YesNoDialog dialog = new YesNoDialog(
                () -> {
                    Call<DeleteResponse> call = feedbackService.delete(item.getFeedbackID());

                    call.enqueue(new Callback<DeleteResponse>() {
                        @Override
                        public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                            if (response.isSuccessful() && response.body().getDeleted()){
                                showSuccessDialog("Delete success!");
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteResponse> call, Throwable t) {
                            showFailDialog("Delete fail");
                            Log.e("Fail to delete this feedback", t.getLocalizedMessage());
                        }
                    });
                }, "Do you want to delete this feedback?");
        dialog.show(fragmentTransaction, "dialog for feedback");
    }

    private void clickDetail() {
        bundle.putString("mission", SystemConstant.DETAIL);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_nav_feedback_to_review_feedback_fragment, bundle);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showFailDialog(String message) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }

    private void showSuccessDialog(String message) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message);
        newFragment.show(ft, "dialog success");
    }
}