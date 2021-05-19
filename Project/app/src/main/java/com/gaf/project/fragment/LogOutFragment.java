package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaf.project.LoginActivity;
import com.gaf.project.MainActivity;
import com.gaf.project.R;
import com.gaf.project.authentication.AuthenticationResponse;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.YesNoDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Class;

public class LogOutFragment extends Fragment {

    public LogOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_out, container, false);
        // Inflate the layout for this fragment
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();


        final YesNoDialog dialog = new YesNoDialog(
                () -> clickOke(),
                "Do you want to Log Out ?");

        dialog.show(ft, "dialog fail");

        return view;

    }
    private void clickOke() {
        SystemConstant.authenticationResponse = new AuthenticationResponse();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}