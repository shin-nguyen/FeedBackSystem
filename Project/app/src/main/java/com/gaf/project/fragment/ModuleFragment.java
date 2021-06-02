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
<<<<<<< HEAD
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
=======
>>>>>>> parent of d8a46df (fix conflict)
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.ModuleAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.WarningDialog;
import com.gaf.project.model.Module;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.response.ModuleResponse;
import com.gaf.project.service.ModuleService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleFragment extends Fragment {

    private View view;
    private RecyclerView rcvModule;
    private ModuleAdapter adapter;
<<<<<<< HEAD
    private ModuleViewModel moduleViewModel;
=======
    private List<Module> moduleList;
    private ModuleService moduleService;
>>>>>>> parent of d8a46df (fix conflict)
    private  String userRole;
    private Button btnAddModule;
    public ModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleService = ApiUtils.getModuleService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userRole = SessionManager.getInstance().getUserRole();

        view = inflater.inflate(R.layout.fragment_module, container, false);
        btnAddModule = view.findViewById(R.id.btn_add_module);
        rcvModule = view.findViewById(R.id.rcv_module);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvModule.setLayoutManager(linearLayoutManager);

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


        if(userRole.equals(SystemConstant.ADMIN_ROLE)){
            Call<ModuleResponse> call =  moduleService.loadModuleAdmin();
            setAdapter(call);

            btnAddModule.setOnClickListener(v ->{
                Bundle bundle = new Bundle();
                bundle.putString("mission", SystemConstant.ADD);
                Navigation.findNavController(view).navigate(R.id.action_nav_module_to_addModuleFragment,bundle);
            });
        }else if(userRole.equals(SystemConstant.TRAINER_ROLE)){
            btnAddModule.setVisibility(View.GONE);

            Call<ModuleResponse> call =  moduleService.loadModuleTrainer();
            setAdapter(call);
        }else if(userRole.equals(SystemConstant.TRAINEE_ROLE)){
            btnAddModule.setVisibility(View.GONE);

            Call<ModuleResponse> call =  moduleService.loadModuleTrainee();
            setAdapter(call);
        }
        rcvModule.setAdapter(adapter);
        return  view;
    }

    private void setAdapter(Call<ModuleResponse> call){

        call.enqueue(new Callback<ModuleResponse>() {
            @Override
            public void onResponse(Call<ModuleResponse> call, Response<ModuleResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    moduleList = response.body().getModules();
                    adapter.setData(moduleList);
                }
            }

            @Override
            public void onFailure(Call<ModuleResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });
    }

    private void clickUpdate(Module item) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("mModule", item);
            bundle.putString("mission", SystemConstant.UPDATE);

            Navigation.findNavController(view).navigate(R.id.action_nav_module_to_addModuleFragment, bundle);
    }

    private void clickDelete(Module item){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
<<<<<<< HEAD
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

=======

        final WarningDialog dialog = new WarningDialog(
                () -> {
                    Call<DeleteResponse> call =  moduleService.delete(item.getModuleID());

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
>>>>>>> parent of d8a46df (fix conflict)
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