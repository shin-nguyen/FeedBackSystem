package com.gaf.project.fragment;

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
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.FeedbackAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.WarningDialog;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.TypeFeedback;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.FeedbackResponse;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackFragment extends Fragment{

    private View view;
    private RecyclerView recyclerViewFeedback;
    private FeedbackAdapter feedBackAdapter;
    private List<Feedback> listFeedBack;
    private FeedbackService feedbackService;
    private Button btnAdd;

    public FeedBackFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackService = ApiUtils.getFeedbackService();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        btnAdd =view.findViewById(R.id.btn_add_feedback);
        listFeedBack = new ArrayList<>();

        recyclerViewFeedback = view.findViewById(R.id.rcv_feedback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewFeedback.setLayoutManager(linearLayoutManager);

        feedBackAdapter = new FeedbackAdapter(new FeedbackAdapter.IClickItem() {
            @Override
            public void update(Feedback item) {
                clickUpdate(item);
            }

            @Override
            public void detail(Feedback item) {
                clickDetail(item);
            }

            @Override
            public void delete(Feedback item) {
                clickDelete(item);
            }
        });

        Call<FeedbackResponse> call =  feedbackService.getListFeedback();
        setAdapter(call);
        recyclerViewFeedback.setAdapter(feedBackAdapter);

        //open fragment create new feedback by button
        Button btnAddFeedBack = view.findViewById(R.id.btn_add_feedback);
        btnAddFeedBack.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("mission", SystemConstant.ADD);

            Navigation.findNavController(view).navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
        });

        return view;
    }

    private void clickDetail(Feedback item) {
    }

    private void clickUpdate(Feedback item) {

    }

    private void setAdapter(Call<FeedbackResponse> call){

        call.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    listFeedBack = response.body().getFeedbacks();
                    feedBackAdapter.setData(listFeedBack);
                }
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });
    }
    private void clickDelete(Feedback item){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        final WarningDialog dialog = new WarningDialog(
                () -> {
                    Call<DeleteResponse> call =  feedbackService.delete(item.getFeedbackID());

                    call.enqueue(new Callback<DeleteResponse>() {
                        @Override
                        public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                            if (response.isSuccessful()&&response.body().getDeleted()){
                                showSuccessDialog("Delete success!");
                            }
                        }
                        @Override
                        public void onFailure(Call<DeleteResponse> call, Throwable t) {
                            showFailDialog("Delete success!");
                            Log.e("Error",t.getLocalizedMessage());
                        }
                    });
                },
                "Do you want to delete this Class?");
        dialog.show(ft, "dialog success");
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
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