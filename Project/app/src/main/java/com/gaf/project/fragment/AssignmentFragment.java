package com.gaf.project.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.YesNoDialog;
import com.gaf.project.model.Assignment;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;


import java.util.ArrayList;
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
    private LinearLayout searchFiled;
    private Button btnAdd;
    private Boolean homeRole;

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

        try{
            homeRole = getArguments().getBoolean("home_role");
            searchFiled = view.findViewById(R.id.search_field);

            if(homeRole==true){
                searchFiled.setVisibility(View.GONE);
            }
        }catch (Exception ex){

        }


        String userRole = SessionManager.getInstance().getUserRole();


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
        if(!userRole.equals(SystemConstant.ADMIN_ROLE)){
            btnAdd.setVisibility(View.GONE);
        }
//        btnAddAssignment.setVisibility(View.GONE);//hide button
//        btnAddAssignment.setVisibility(View.VISIBLE);//show button

        btnAdd.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_nav_assignment_to_add_assignment_fragment);
        });

        return view;
    }

    private void clickUpdate(Assignment item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_assignment_to_edit_assignment_fragment,bundle);
    }

    private void clickDelete(Assignment item){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        final YesNoDialog dialog = new YesNoDialog(
                () -> {
                    Call<DeleteResponse> call =  assignmentService.delete(item.getMClass().getClassID(),
                                                                            item.getModule().getModuleID(),
                                                                            item.getTrainer().getUserName());

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
                "Do you want to delete this Assignment?");


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