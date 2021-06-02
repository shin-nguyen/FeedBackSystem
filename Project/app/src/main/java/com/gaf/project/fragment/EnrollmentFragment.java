package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
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
import com.gaf.project.viewmodel.EnrollmentViewModel;

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
    private Spinner spnClass  ;
    private ArrayAdapter<Class> enrollmentArrayAdapter;
    private EnrollmentViewModel enrollmentViewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enrollmentViewModel = new ViewModelProvider(this).get(EnrollmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_enrollment, container, false);
        spnClass = view.findViewById(R.id.spinner_class);

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

        //get list class from view model to set spinner
        enrollmentViewModel.getListClassLiveData().observe(getViewLifecycleOwner(), classes -> {
            Class clazz = new Class(0, "All");
            List<Class> classList = classes;
            if (classList.contains(clazz) == false){
                classList.add(0, clazz);
            }
            enrollmentArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, classList);
            spnClass.setAdapter(enrollmentArrayAdapter);
        });

        //get list enrollment from view model to show list
        enrollmentViewModel.getListEnrollmentLiveData().observe(getViewLifecycleOwner(), enrollments -> enrollmentAdapter.setData(enrollments));

        recyclerViewEnrollment.setAdapter(enrollmentAdapter);

        spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Class clazz = enrollmentArrayAdapter.getItem(position);
                enrollmentAdapter.getFilter().filter(clazz.getClassName(), count -> {
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    private void clickDelete(Enrollment item){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        final WarningDialog dialog = new WarningDialog(
                () -> {
                    showDialog(enrollmentViewModel.delete(item.getTrainee().getUserName(), item.getMClass().getClassID()), "Delete Enrollment");
                },
                "Do you want to delete this item?");
        dialog.show(ft, "dialog success");
    }

    private void clickDetail(Enrollment item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_enrollment_to_detailEnrollmentFragment, bundle);
    }

    private void clickUpdate(Enrollment item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);

        Navigation.findNavController(view).navigate(R.id.action_nav_enrollment_to_editEnrollmentFragment, bundle);
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
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

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message, () -> {
        });
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }

}