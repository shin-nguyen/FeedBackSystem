package com.gaf.project.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;

public class SuccessDialog extends DialogFragment {

    private String mission;

    public SuccessDialog(String mission) {
        this.mission = mission;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.success_dialog, null);

        Button confirmBtn = view.findViewById(R.id.confirm_button);
        confirmBtn.setOnClickListener(v -> dismiss());

        TextView message = view.findViewById(R.id.dialog_message);
        if (mission == SystemConstant.ADD){
            message.setText("Add Success!");
        }
        else if (mission == SystemConstant.UPDATE){
            message.setText("Update Success!");
        }
        else if (mission == SystemConstant.DELETE) {
            message.setText("Delete Success!");
        }
//        if (getArguments() != null)
//        {
//            TextView message = view.findViewById(R.id.dialog_message);
//            message.setText(getArguments().getString("placeholder", ""));
//        }

        builder.setView(view);
        return builder.create();
    }

}
