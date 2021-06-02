package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.adapter.FeedbackAdapter;
import com.gaf.project.adapter.FeedbackTraineeAdapter;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Feedback;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.viewmodel.AssignmentViewModel;
import com.gaf.project.viewmodel.ClassViewModel;
import com.gaf.project.viewmodel.CommentViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeHomeFragment extends Fragment {

    private View view;
    private AssignmentViewModel assignmentViewModel;
    private RecyclerView recyclerViewAssignment;
    private FeedbackTraineeAdapter feedbackTraineeAdapter;
    private CommentViewModel commentViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        assignmentViewModel.initDataByTrainee();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_trainee_home, container, false);


        recyclerViewAssignment = view.findViewById(R.id.rcv_feedback_for_trainee);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewAssignment.setLayoutManager(linearLayoutManager);


        feedbackTraineeAdapter = new FeedbackTraineeAdapter(item -> {
            doFeedback(item);
        });
        assignmentViewModel.getListAssignmentOfTrainerLiveData().observe(getViewLifecycleOwner(),assignments -> {

            feedbackTraineeAdapter.setData(assignments);
        });

        recyclerViewAssignment.setAdapter(feedbackTraineeAdapter);

        return view;
    }

<<<<<<< HEAD
=======
    private void setAssignmentAdapter(Call<AssignmentResponse> call){
        call.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    listAssignment = response.body().getAssignments();
                    feedbackTraineeAdapter.setData(listAssignment);
                    Log.e("Success","Assignment get success");
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Call API fail!");
            }
        });
    }

>>>>>>> parent of d8a46df (fix conflict)
    private void doFeedback(Assignment item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_trainee_home_fragment_to_doFeedbackFragment,bundle);
    }
    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

}