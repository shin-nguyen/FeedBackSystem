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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.ClassAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Class;
import com.gaf.project.model.Module;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;

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

        //Set layout manager -> recyclerView Status
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvClass = view.findViewById(R.id.rcv_class);
        rcvClass.setLayoutManager(linearLayoutManager);


        btnAddClass = view.findViewById(R.id.btn_add_class);

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

        btnAddClass.setOnClickListener(v ->{
            Navigation.findNavController(view).navigate(R.id.action_nav_class_to_add_class_fragment);
        });



        //Set value adapter for Adapter
        classList = new ArrayList<>();
        Call<ClassResponse> call =  classService.loadListClass(
                "Bearer "+ SystemConstant.authenticationResponse.getJwt()
        );

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

        rcvClass.setAdapter(adapter);
        return view;
    }

    private void clickUpdate(Class item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mClass", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_class_to_add_class_fragment,bundle);
    }

    private void clickDelete(Class item){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View viewBuilder=LayoutInflater.from(getContext()).inflate(R.layout.warning_dialog,null);

        builder.setView(viewBuilder);

        final AlertDialog warningDialog=builder.create();


        TextView warningContent = viewBuilder.findViewById(R.id.txt_warning_content);
        warningContent.setText("Do you want to delete this Class?");

        warningDialog.show();

        Button btnCancel = viewBuilder.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(vi->{
            warningDialog.dismiss();
        });

        Button btnYes = viewBuilder.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(v->{
            warningDialog.dismiss();

            Call<DeleteResponse> call =  classService.delete(
                    "Bearer "+ SystemConstant.authenticationResponse.getJwt(),
                    item.getClassID()
                    );
           call.enqueue(new Callback<DeleteResponse>() {
               @Override
               public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                   if (response.isSuccessful()&&response.body().getDeleted()){
                       AlertDialog.Builder builderSuccess=new AlertDialog.Builder(getContext());
                       View viewBuilderSuccess=LayoutInflater.from(getContext()).inflate(R.layout.success_dialog,null);

                       builder.setView(viewBuilderSuccess);

                       TextView warningSuccessContent = viewBuilderSuccess.findViewById(R.id.txt_success_content);
                       warningSuccessContent.setText("Delete Success!");



                       final AlertDialog successDialog=builder.create();
                       successDialog.show();

                       Button btnSuccessCancel = viewBuilderSuccess.findViewById(R.id.btn_ok);
                       btnSuccessCancel.setOnClickListener(vi -> {
                           successDialog.dismiss();
                       });

                   }

               }

               @Override
               public void onFailure(Call<DeleteResponse> call, Throwable t) {

               }
           });

        });

    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
    //switch to another fragment
//    private void replaceFragment(Fragment fragment){
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id., fragment);
//        fragmentTransaction.commit();
//    }

}