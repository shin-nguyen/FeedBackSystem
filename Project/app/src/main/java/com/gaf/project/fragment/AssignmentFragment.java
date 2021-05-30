package com.gaf.project.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.gaf.project.dialog.WarningDialog;
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
    private SearchView searchAssignment;
    private Button btnAdd, btnSearch;
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

        searchFiled = view.findViewById(R.id.search_field);
        searchAssignment = view.findViewById(R.id.sv_assignment);
        btnAdd = view.findViewById(R.id.btn_add_assignment);
        btnSearch = view.findViewById(R.id.btn_search);

        String userRole = SessionManager.getInstance().getUserRole();
        if(!userRole.equals(SystemConstant.ADMIN_ROLE)){
            btnAdd.setVisibility(View.GONE);
        }

        try{
            homeRole = getArguments().getBoolean("home_role");
            if(homeRole==true) {
                searchFiled.setVisibility(View.GONE);
            }
        }catch (Exception exception){

        }

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

        listAssignment = new ArrayList<>();

        if(userRole.equals(SystemConstant.ADMIN_ROLE)){
            Call<AssignmentResponse> call =  assignmentService.loadListAssignment();
            setAssignmentAdapter(call);
        }

        if(userRole.equals(SystemConstant.TRAINER_ROLE)){
            Call<AssignmentResponse> call =  assignmentService.loadListAssignmentByTrainer();
            setAssignmentAdapter(call);
        }

        btnAdd.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_nav_assignment_to_add_assignment_fragment);
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentAdapter.getFilter().filter(searchAssignment.getQuery());
            }
        });

        searchAssignment.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                reloadFragment();
                return false;
            }
        });

        recyclerViewAssignment.setAdapter(assignmentAdapter);

        return view;
    }

    private void setAssignmentAdapter(Call<AssignmentResponse> call){
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
    }

    private void clickDelete(Assignment item){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        final WarningDialog dialog = new WarningDialog(
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
                            showFailDialog("Delete fail!");
                            Log.e("Error",t.getLocalizedMessage());
                        }
                    });

                    reloadFragment();
                },
                "Do you want to delete this Assignment?");


        dialog.show(ft, "dialog success");
    }

    private void clickUpdate(Assignment item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_assignment_to_edit_assignment_fragment,bundle);
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {

            }
        });
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }

    public void reloadFragment(){
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}