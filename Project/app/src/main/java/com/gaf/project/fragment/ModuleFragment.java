package com.gaf.project.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.ModuleAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.WarningDialog;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ModuleService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;
import com.gaf.project.viewmodel.AdminViewModel;
import com.gaf.project.viewmodel.ModuleViewModel;
import com.gaf.project.viewmodel.QuestionViewModel;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleFragment extends Fragment {

    private View view;
    private RecyclerView rcvModule;
    private ModuleAdapter adapter;
    private ModuleViewModel moduleViewModel;
    private  String userRole;
    private Button btnAddModule;
    public ModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        moduleViewModel.initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_module, container, false);
        initComponents(view);

        adapter = new ModuleAdapter(new ModuleAdapter.IClickItem() {
            @Override
            public void update(Module item) {
                clickUpdate(item);
            }

            @Override
            public void delete(Module item) {
                clickDelete(item);
            }
        });

        userRole = SessionManager.getInstance().getUserRole();
        if(userRole.equals(SystemConstant.ADMIN_ROLE)){
            btnAddModule.setOnClickListener(v ->{
                Bundle bundle = new Bundle();
                bundle.putString("mission", SystemConstant.ADD);
                Navigation.findNavController(view).navigate(R.id.action_nav_module_to_addModuleFragment,bundle);
            });
        }else if(userRole.equals(SystemConstant.TRAINER_ROLE)){
            btnAddModule.setVisibility(View.GONE);
        }else if(userRole.equals(SystemConstant.TRAINEE_ROLE)){
            btnAddModule.setVisibility(View.GONE);
        }

        moduleViewModel.getmListModuleLiveData().observe(getViewLifecycleOwner(),modules -> {
            adapter.setData(modules);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvModule.setLayoutManager(linearLayoutManager);
        rcvModule.setAdapter(adapter);
        return view;
    }

    private void initComponents(View view) {
        btnAddModule = view.findViewById(R.id.btn_add_module);
        rcvModule = view.findViewById(R.id.rcv_module);
    }

    private void clickUpdate(Module item) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("mModule", item);
            bundle.putString("mission", SystemConstant.UPDATE);

            Navigation.findNavController(view).navigate(R.id.action_nav_module_to_addModuleFragment, bundle);
    }

    private void clickDelete(Module item){

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        final WarningDialog dialog;
        Boolean checkDeleteFlag = check(item.getStartTime());
        if (checkDeleteFlag) {
            dialog = new WarningDialog(
                    () -> {
                        showDialog(moduleViewModel.delete(item),"Delete");
                    },
                    "This Module has been started. You readly want to delete this Module?");
        } else {
            dialog = new WarningDialog(
                    () -> {
                        ;
                        showDialog(moduleViewModel.delete(item),"Delete");
                    },
                    "Do you want to delete this Module?");
        }
        dialog.show(ft, "dialog success");
    }

    private Boolean check(Date startTime) {
        Date date = new Date();
        if (date.compareTo(startTime)<0){
            return false;
        }
        return  true;
    }

    public void showDialog(MutableLiveData<String> actionStatus, String action){
        actionStatus.observe(getViewLifecycleOwner(),s -> {
            if(s.equals(SystemConstant.SUCCESS)){
                showSuccessDialog(action+" Success!!");
            }else {
                showFailDialog(action+" Fail!!");
            }
        });

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

}