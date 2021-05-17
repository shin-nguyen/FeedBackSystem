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

public class FailDialog extends DialogFragment {

    private String message;

    public FailDialog(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fail_dialog, null);

        TextView dialogMessage = view.findViewById(R.id.txt_fail_dialog_message);
        dialogMessage.setText(message);

        Button confirmBtn = view.findViewById(R.id.btn_fail_confirm);
        confirmBtn.setOnClickListener(v -> dismiss());

//        if (getArguments() != null)
//        {
//            TextView message = view.findViewById(R.id.dialog_message);
//            message.setText(getArguments().getString("placeholder", ""));
//        }

        builder.setView(view);
        return builder.create();
    }
}
