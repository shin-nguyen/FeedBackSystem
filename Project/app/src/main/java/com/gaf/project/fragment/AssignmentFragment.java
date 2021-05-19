package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import android.util.Log;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.AssignmentId;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Module;
import com.gaf.project.model.Trainee;
import com.gaf.project.model.Trainer;
import com.gaf.project.model.TypeFeedback;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.utils.ApiUtils;


import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentFragment extends Fragment{

    private View view;
    private AssignmentService assignmentService;
    private RecyclerView recyclerViewAssignment;
    private AssignmentAdapter assignmentAdapter;
    private List<Assignment> listAssignment;
    private Button btnAdd;

    public AssignmentFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentService = ApiUtils.getAssignmentService();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment, container, false);

        recyclerViewAssignment = view.findViewById(R.id.rcv_assignment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewAssignment.setLayoutManager(linearLayoutManager);

        assignmentAdapter = new AssignmentAdapter(new AssignmentAdapter.IClickItem() {
            @Override
            public void update(Assignment item) {
                clickUpdate(item);
            }

            @Override
            public void delete(Assignment item) {
                clickDelete(item);
            }
        });

            //Set value adapter for Adapter
            listAssignment = new ArrayList<>();
            Call<AssignmentResponse> call =  assignmentService.loadListAssignment();

            call.enqueue(new Callback<AssignmentResponse>() {
                @Override
                public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                    if (response.isSuccessful()&&response.body()!=null){
                        listAssignment = response.body().getAssignments();
                        assignmentAdapter.setData(listAssignment);
                        Log.e("Success","Assigment get success");
                    }
                }

                @Override
                public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Call API fail!");
                }
            });

        recyclerViewAssignment.setAdapter(assignmentAdapter);

        btnAdd = view.findViewById(R.id.btn_add_assignment);
//        btnAddAssignment.setVisibility(View.GONE);//hide button
//        btnAddAssignment.setVisibility(View.VISIBLE);//show button

        btnAdd.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("mission", SystemConstant.ADD);

            Navigation.findNavController(view).navigate(R.id.action_nav_assignment_to_add_assignment_fragment,bundle);
        });

        return view;
    }

    private void clickUpdate(Assignment item) {
        Bundle bundle = new Bundle();
        bundle.putString("mission", SystemConstant.UPDATE);
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_assignment_to_edit_assignment_fragment,bundle);
    }

    private void clickDelete(Assignment item){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View viewBuilder=LayoutInflater.from(getContext()).inflate(R.layout.warning_dialog,null);

        builder.setView(viewBuilder);

        final AlertDialog warningDialog=builder.create();

        TextView warningContent = viewBuilder.findViewById(R.id.txt_warning_content);
        warningContent.setText("Do you want to delete this Assignment?");

        warningDialog.show();

        Button btnCancel = viewBuilder.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(vi->{
            warningDialog.dismiss();
        });

        Button btnYes = viewBuilder.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(v->{

            warningDialog.dismiss();

            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            SuccessDialog newFragment = new SuccessDialog("Delete success!");
            newFragment.show(ft, "dialog success");
        });
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}