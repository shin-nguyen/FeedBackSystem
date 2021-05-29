package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Assignment;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinFragment extends DialogFragment {

    public static JoinFragment newInstance(){
        return new JoinFragment();
    }

    private View view;
    private String registrationCode;
    private List<Assignment> listAssignment;
    private AssignmentService assignmentService;
    private String message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.JoinDialog);
        assignmentService = ApiUtils.getAssignmentService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_join, container, false);

        //close dialog
        Button closeButton = view.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(v -> {
            dismiss();
        });

        //get text from edit text
        EditText editText = view.findViewById(R.id.edt_code);

        //submit button
        Button submitButton = view.findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(v -> {
            registrationCode = editText.getText().toString();
            joinInClass(registrationCode);
        });

        return view;
    }

    private void joinInClass(String code) {
//        Toast.makeText(getContext(), code, Toast.LENGTH_LONG).show();
        //load list assignment
        listAssignment = new ArrayList<>();
        Call<AssignmentResponse> call =  assignmentService.loadListAssignment();
        call.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    listAssignment = response.body().getAssignments();
                    Log.e("Success","Assigment get success");

                    for (Assignment item: listAssignment
                    ) {
                        if (registrationCode.equals(item.getRegistrationCode())){
                            message = "Join Success!";
                            showSuccessDialog(message);
                        }else if (registrationCode != item.getRegistrationCode()){
                            message = "Invalid Registration Code!!!";
                            showFailDialog(message);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
                showToast("Call API fail!");
            }
        });
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

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}