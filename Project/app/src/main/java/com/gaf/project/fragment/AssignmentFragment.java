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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.gaf.project.model.Feedback;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.FeedbackResponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.FeedbackService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;
import com.gaf.project.viewmodel.AssignmentViewModel;


import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentFragment extends Fragment{

    private View view;
    private RecyclerView recyclerViewAssignment;
    private AssignmentAdapter assignmentAdapter;
    private LinearLayout searchFiled;
    private SearchView searchAssignment;
    private Button btnAdd, btnSearch;
    private Boolean homeRole;
    private String userRole;
    private AssignmentViewModel assignmentViewModel;

    public AssignmentFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
    }

    @Override
    public void onStart(){
        super.onStart();

        //Update data every time you enter Fragment
        assignmentViewModel.initData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment, container, false);

        //get role of current user from session manager
        userRole = SessionManager.getInstance().getUserRole();

        initView();

        //declare recyclerview(view, adapter and set layout)
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

        //if user is admin, get list all assignment, then put it in adapter
        if(userRole.equals(SystemConstant.ADMIN_ROLE)){
            assignmentViewModel.getListAssignmentLiveData().observe(getViewLifecycleOwner(), new Observer<List<Assignment>>() {
                @Override
                public void onChanged(List<Assignment> list) {
                    if(list.isEmpty()){
                        assignmentAdapter.setData(null);
                    }
                    assignmentAdapter.setData(list);
                }
            });
        }

        //if user is trainer get list assignment of user, then put it in adapter
        if(userRole.equals(SystemConstant.TRAINER_ROLE)){
            assignmentViewModel.getListAssignmentOfTrainerLiveData().observe(getViewLifecycleOwner(), new Observer<List<Assignment>>() {
                @Override
                public void onChanged(List<Assignment> list) {
                    assignmentAdapter.setData(list);
                }
            });
        }

        //put adapter to recyclerview
        recyclerViewAssignment.setAdapter(assignmentAdapter);

        //set event for button add
        btnAdd.setOnClickListener(v->{

            //go to add assignment page
            Navigation.findNavController(view).navigate(R.id.action_nav_assignment_to_add_assignment_fragment);
        });

        //set event for button search
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //filter by content of search box
                assignmentAdapter.getFilter().filter(searchAssignment.getQuery());
            }
        });

        //reload the page at the end of the search action
        searchAssignment.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                reloadFragment();
                return false;
            }
        });

        return view;
    }

    /*set event to delete button
    *delete assignment and show dialog*/
    private void clickDelete(Assignment item){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if(checkToDelete(item)){
            final WarningDialog dialog = new WarningDialog(
                    () -> {
                        showDialog(assignmentViewModel.deleteAssignment(item),"Delete");
                    },
                    "An active Module and Class has been assigned to this assignment. Do you really want to delete this?");
            dialog.show(ft, "dialog success");
        }else {
            final WarningDialog dialog = new WarningDialog(
                    () -> {
                        showDialog(assignmentViewModel.deleteAssignment(item),"Delete");
                    },
                    "Do you want to delete this Assignment?");
            dialog.show(ft, "dialog success");
        }

    }

    /*set event for update button
    *go to edit assignment page */
    private void clickUpdate(Assignment item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_assignment_to_edit_assignment_fragment,bundle);
    }

  /*check to delete assignment
    if assignment has a valid class or module and has not been deleted, it returns false and vice versa*/
    public Boolean checkToDelete(Assignment assignment){
        if((assignment.getMClass().isDeleted()==false && assignment.getMClass().getEndTime().compareTo(new Date())>0)
                || (assignment.getModule().isDeleted()==false && assignment.getModule().getEndTime().compareTo(new Date())>0)){
            return true;
        }
        return false;
    }

    //show toast
    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

    //show dialog when the action is finished
    public void showDialog(MutableLiveData<String> actionStatus, String action){
        actionStatus.observe(getViewLifecycleOwner(),s -> {
            if(s.equals(SystemConstant.SUCCESS)){
                showSuccessDialog(action+" Success!!");
            }else {
                showFailDialog(action+" Fail!!");
            }
        });
    }

    //show fail dialog
    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }

    //show success dialog
    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, new SuccessDialog.IClick() {
            @Override
            public void changeFragment() {

            }
        });
        newFragment.show(ft, "dialog success");
    }

    //reload page
    public void reloadFragment(){
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }

    //declare all view
    private void initView(){
        searchFiled = view.findViewById(R.id.search_field);
        searchAssignment = view.findViewById(R.id.sv_assignment);
        btnAdd = view.findViewById(R.id.btn_add_assignment);
        btnSearch = view.findViewById(R.id.btn_search);

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
    }
}