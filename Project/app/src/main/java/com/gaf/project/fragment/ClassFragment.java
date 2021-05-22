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
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment {
    private ClassAdapter adapter;
    private RecyclerView rcvClass;
    private ClassService classService;
    private List<Class> classList;
    private TextView title;
    private Button btnAddClass;
    private  View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classService = ApiUtils.getClassService();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_class, container, false);

        title = view.findViewById(R.id.txt_title);

        btnAddClass = view.findViewById(R.id.btn_add_class);

        btnAddClass.setOnClickListener(v ->{
            Bundle bundle = new Bundle();
            bundle.putString("mission", SystemConstant.ADD);
            Navigation.findNavController(view).navigate(R.id.action_nav_class_to_add_class_fragment,bundle);
        });

        String userRole = SessionManager.getInstance().getUserRole();

        if(userRole.equals(SystemConstant.ADMIN_ROLE)){

            title.setText("Class List");
            //Set value adapter for Adapter
            classList = new ArrayList<>();
            Call<ClassResponse> call =  classService.loadListClass();

            call.enqueue(new Callback<ClassResponse>() {
                @Override
                public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {
                    if (response.isSuccessful()&&response.body()!=null){
                        classList = response.body().getClasss();
                        adapter.setData(classList);
                    }
                }

                @Override
                public void onFailure(Call<ClassResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Error");
                }
            });

        }

        if(userRole.equals(SystemConstant.TRAINER_ROLE)){

            title.setText("List Class");
            btnAddClass.setVisibility(View.GONE);

        }

        if(userRole.equals(SystemConstant.TRAINEE_ROLE)){

            title.setText("Class List");
            btnAddClass.setVisibility(View.GONE);

        }

        //Set layout manager -> recyclerView Status
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvClass = view.findViewById(R.id.rcv_class);
        rcvClass.setLayoutManager(linearLayoutManager);

        adapter =new ClassAdapter(new ClassAdapter.IClickItem() {
            @Override
            public void update(Class item) {
                clickUpdate(item);
            }
            @Override
            public void delete(Class item) {
                clickDelete(item);
            }
        });

        rcvClass.setAdapter(adapter);

        return view;
    }

    private void clickUpdate(Class item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mClass", item);
        bundle.putString("mission", SystemConstant.UPDATE);

        Navigation.findNavController(view).navigate(R.id.action_nav_class_to_add_class_fragment,bundle);
    }

    private void clickDelete(Class item){

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();


        final WarningDialog dialog = new WarningDialog(
                () -> {
                    Call<DeleteResponse> call =  classService.delete(item.getClassID());

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