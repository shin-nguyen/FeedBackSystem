package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.EnrollmentAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.dialog.WarningDialog;
import com.gaf.project.model.Class;
import com.gaf.project.model.Enrollment;
import com.gaf.project.model.Trainee;
import com.gaf.project.response.ClassResponse;
import com.gaf.project.response.DeleteResponse;
import com.gaf.project.service.ClassService;
import com.gaf.project.utils.ApiUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollmentFragment extends Fragment {

    private View view;
    private RecyclerView recyclerViewEnrollment;
    private EnrollmentAdapter enrollmentAdapter;
    private List<Class> listClass;
    private ClassService classService;
    private List<Enrollment> enrollmentList;
    private  Spinner spnClass  ;
    private Button btnAddEnrollment;
    private TextView title;
    
    public EnrollmentFragment() {
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classService = ApiUtils.getClassService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_enrollment, container, false);
        btnAddEnrollment = view.findViewById(R.id.btn_add_enrollment);
        title = view.findViewById(R.id.txt_title);

        enrollmentAdapter =  new EnrollmentAdapter(new EnrollmentAdapter.IClickItem() {
            @Override
            public void detail(Enrollment item) {
                clickDetail(item);
            }

            @Override
            public void update(Enrollment item) {
                clickUpdate(item);
            }

            @Override
            public void delete(Enrollment item) {
                clickDelete(item);
            }
        });

        recyclerViewEnrollment = view.findViewById(R.id.rcv_enrollment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewEnrollment.setLayoutManager(linearLayoutManager);

        listClass = new ArrayList<>();
        Call<ClassResponse> call =  classService.loadListClass();
        call.enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {
                listClass = response.body().getClasss();
            }

            @Override
            public void onFailure(Call<ClassResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Error");
            }
        });

        if (listClass!=null) {
            for (Class mClass:listClass) {
                for (Trainee trainee : mClass.getTrainees())
                enrollmentList.add(new Enrollment(mClass,trainee));
            }
        }

        enrollmentAdapter.setData(enrollmentList);

        return view;
    }
    private void clickDelete(Enrollment item){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        final WarningDialog dialog = new WarningDialog(
                () -> {
                    Call<Class> call =  classService.deleteTrainee(item.getTrainee().getUserName(),item.getMClass());

                    call.enqueue(new Callback<Class>() {
                        @Override
                        public void onResponse(Call<Class> call, Response<Class> response) {
                            if (response.isSuccessful()&&response.body()!=null){
                                showSuccessDialog("Delete success!");
                            }
                        }
                        @Override
                        public void onFailure(Call<Class> call, Throwable t) {
                            showFailDialog("Delete success!");
                            Log.e("Error",t.getLocalizedMessage());
                        }
                    });
                },
                "Do you want to delete this Class?");
        dialog.show(ft, "dialog success");
    }

    private void clickDetail(Enrollment item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_enrollment_to_detailEnrollmentFragment, bundle);
    }

    private void clickUpdate(Enrollment item) {
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