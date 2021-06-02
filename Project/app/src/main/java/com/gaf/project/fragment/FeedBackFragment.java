package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.gaf.project.model.Feedback;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.FeedbackResponse;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;
import com.gaf.project.viewmodel.FeedBackViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackFragment extends Fragment{

    private View view;
    private RecyclerView recyclerViewFeedback;
    private FeedbackAdapter feedBackAdapter;
    private FeedBackViewModel feedBackViewModel;
    private FeedbackService feedbackService;
    private NavController navigation;

    private Bundle bundle = new Bundle();

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackService = ApiUtils.getFeedbackService();
        feedBackViewModel = new ViewModelProvider(this).get(FeedBackViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        feedBackViewModel.initData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);

        String userRole = SessionManager.getInstance().getUserRole();

        if(userRole.equals(SystemConstant.ADMIN_ROLE)){

            feedBackViewModel.getListFeedBackLiveData().observe(getViewLifecycleOwner(), new Observer<List<Feedback>>() {
                @Override
                public void onChanged(List<Feedback> feedbacks) {
                    feedBackAdapter.setData(feedbacks);
                }
            });

            //open fragment create new feedback by button
            Button btnAddFeedBack = view.findViewById(R.id.btn_add_feedback);
            btnAddFeedBack.setOnClickListener(v -> {
                clickAdd();
            });
        }

        feedBackAdapter = new FeedbackAdapter(new FeedbackAdapter.IClickItem() {
            @Override
            public void detail(Feedback item) {
                clickDetail(item);
            }

            @Override
            public void update(Feedback item) {
                clickUpdate(item);
            }

            @Override
            public void delete(Feedback item) {
                clickDelete(item);
            }
        });



        recyclerViewFeedback = view.findViewById(R.id.rcv_feedback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewFeedback.setLayoutManager(linearLayoutManager);
        recyclerViewFeedback.setAdapter(feedBackAdapter);

        return view;
    }

    private void clickAdd() {
        bundle.putString("mission", SystemConstant.ADD);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
    }

    private void clickUpdate(Feedback item) {
        bundle.putString("mission", SystemConstant.UPDATE);
        bundle.putSerializable("item", item);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
    }

    private void clickDelete(Feedback item) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        final WarningDialog dialog = new WarningDialog(
                () -> {
                    Call<DeleteResponse> call = feedbackService.delete(item.getFeedbackID());
                    call.enqueue(new Callback<DeleteResponse>() {
                        @Override
                        public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                            if (response.isSuccessful() && response.body().getDeleted()){
                                showSuccessDialog("Delete success!");
                                reloadFragment();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteResponse> call, Throwable t) {
                            showFailDialog("Delete fail!");
                            Log.e("Fail to delete this feedback", t.getLocalizedMessage());
                        }
                    });
                    reloadFragment();
                }, "Do you want to delete this feedback?");
        dialog.show(fragmentTransaction, "dialog for feedback");
    }

    private void clickDetail(Feedback item) {
        bundle.putString("mission", SystemConstant.DETAIL);
        bundle.putSerializable("feedback", item);
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
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {

            }
        });
        newFragment.show(ft, "dialog success");
    }

    private void reloadFragment(){
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}