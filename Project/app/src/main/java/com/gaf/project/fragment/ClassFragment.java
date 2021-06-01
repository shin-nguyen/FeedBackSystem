package com.gaf.project.fragment;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.ClassAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.WarningDialog;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;
import com.gaf.project.viewmodel.ClassViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment {
    private ClassAdapter adapter;
    private RecyclerView rcvClass;
    private TextView title;
    private ClassViewModel classViewModel;
    private Button btnAddClass;
    private View view;
    private String userRole;
    private Boolean checkDeleteFlag = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        classViewModel.initData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_class, container, false);
        initComponents(view);

        userRole = SessionManager.getInstance().getUserRole();

        if(userRole.equals(SystemConstant.ADMIN_ROLE)){

            title.setText("Class List");
            //Set value adapter for Adapter
            btnAddClass.setOnClickListener(v ->{
                Bundle bundle = new Bundle();
                bundle.putString("mission", SystemConstant.ADD);
                Navigation.findNavController(view).navigate(R.id.action_nav_class_to_add_class_fragment,bundle);
            });
        }

        if(userRole.equals(SystemConstant.TRAINER_ROLE)){
            title.setText("List Class");
            btnAddClass.setVisibility(View.GONE);
        }else if(userRole.equals(SystemConstant.TRAINEE_ROLE)){
            title.setText("Class List");
            btnAddClass.setVisibility(View.GONE);
        }

        adapter =new ClassAdapter(new ClassAdapter.IClickItem() {
            @Override
            public void updateAndDetail(Class item) {
                clickUpdate(item);
            }

            @Override
            public void delete(Class item) {
                clickDelete(item);
            }
        });

        classViewModel.getListClassLiveData().observe(getViewLifecycleOwner(),classes -> {
            adapter.setData(classes);
        });

        //Set layout manager -> recyclerView Status
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvClass.setLayoutManager(linearLayoutManager);
        rcvClass.setAdapter(adapter);

        return view;
    }

    private void initComponents(View view) {
        btnAddClass = view.findViewById(R.id.btn_add_class);
        title = view.findViewById(R.id.txt_title);
        rcvClass = view.findViewById(R.id.rcv_class);
    }

    private void clickUpdate(Class item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mClass", item);

        if(userRole.equals(SystemConstant.ADMIN_ROLE)) {
            bundle.putString("mission", SystemConstant.UPDATE);
            Navigation.findNavController(view).navigate(R.id.action_nav_class_to_add_class_fragment, bundle);
        }
        else{
            Navigation.findNavController(view).navigate(R.id.action_nav_class_to_detailClassFragment, bundle);
        }
    }


    private void clickDelete(Class item){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        final WarningDialog dialog;
        checkDeleteFlag = check(item.getStartTime(),item.getEndTime());
        if (checkDeleteFlag) {
            dialog = new WarningDialog(
                    () -> {
                        classViewModel.deleted(item);
                        showDialog("Delete");
                    },
                    "Class is operating. Do you readly want to delete this class?");
        } else {
            dialog = new WarningDialog(
                    () -> {
                        classViewModel.deleted(item);
                        showDialog("Delete");
                    },
                    "Do you want to delete this item?");
        }
        dialog.show(ft, "dialog success");
    }

    private Boolean check(Date startTime, Date endTime) {
        Date date = new Date();
        if (date.compareTo(startTime)<0 || date.compareTo(endTime)>0){
            return false;
        }
        return  true;
    }

    public void showDialog(String action){
        Boolean actionStatus = classViewModel.getActionStatus().booleanValue();
        if(actionStatus){
            showSuccessDialog(action+" Success!!");
        }else {
            showFailDialog(action+" Fail!!");
        }
    }
    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message,() -> {});
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }
}