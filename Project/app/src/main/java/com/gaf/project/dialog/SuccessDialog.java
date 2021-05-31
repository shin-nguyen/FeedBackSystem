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

    private String message;
    private IClick iClick;

    public SuccessDialog(String message, IClick iClick) {
        this.message = message;
        this.iClick = iClick;
    }

    public interface IClick{
        void changeFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.successful_dialog, null);

        TextView dialogMessage = view.findViewById(R.id.txt_success_dialog_message);
        dialogMessage.setText(message);

        Button confirmBtn = view.findViewById(R.id.btn_success_confirm);
        confirmBtn.setOnClickListener(v -> {
            iClick.changeFragment();
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }

}
