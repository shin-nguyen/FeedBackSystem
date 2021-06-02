package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
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
import com.gaf.project.response.AddTraineeAssignmentResponse;
import com.gaf.project.service.TraineeAssignmentService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinFragment extends DialogFragment {

    public static JoinFragment newInstance(){
        return new JoinFragment();
    }

    private View view;
    private String registrationCode;
    private TraineeAssignmentService traineeAssignmentService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.JoinDialog);
        traineeAssignmentService = ApiUtils.getTraineeAssignmentService();
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
        //
        String userName = SessionManager.getInstance().getUserName();
        Call<AddTraineeAssignmentResponse> addTraineeAssignmentCall = traineeAssignmentService.create(userName, code);
        addTraineeAssignmentCall.enqueue(new Callback<AddTraineeAssignmentResponse>() {
            @Override
            public void onResponse(Call<AddTraineeAssignmentResponse> call, Response<AddTraineeAssignmentResponse> response) {
                if (response.isSuccessful()&&response.body().getAdded() == null){
                    showFailDialog("Some thing went wrong");
                }
                else if (response.isSuccessful()&&response.body().getAdded() == 2){
                    showSuccessDialog("Join success!");
                }
                else if (response.isSuccessful()&&response.body().getAdded() == 1){
                    showFailDialog("Invalid Registration Code!!!");
                }
                else if (response.isSuccessful()&&response.body().getAdded() == 0){
                    showFailDialog("You already join this module, please try another!!!");
                }
                else if (response.body() != null){
                    showFailDialog("Error");
                }
            }

            @Override
            public void onFailure(Call<AddTraineeAssignmentResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
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

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }
}