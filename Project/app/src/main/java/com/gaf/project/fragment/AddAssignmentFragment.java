package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.ShowDialog;
import com.gaf.project.dialog.SuccessDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAssignmentFragment extends Fragment {

    private View view;
    private Button btnSave,btnBack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAssignmentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddAssignmentFragment newInstance(String param1, String param2) {
        AddAssignmentFragment fragment = new AddAssignmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_assignment, container, false);

        final Spinner sprModule = (Spinner) view.findViewById(R.id.spinner_module);
        String[] items_sprMail= new String[]{"sinh@gmail.com", "nguyen@gmail.com", "quyet@gmail.com"};
        ArrayAdapter<String> adapter_sprMail = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items_sprMail);
        sprModule.setAdapter(adapter_sprMail);

        final Spinner sprClass = (Spinner) view.findViewById(R.id.spinner_class);
        String[] items_sprClass= new String[]{"sinh@gmail.com", "nguyen@gmail.com", "quyet@gmail.com"};
        ArrayAdapter<String> adapter_sprClass = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items_sprClass);
        sprClass.setAdapter(adapter_sprClass);

        final Spinner sprTrainer = (Spinner) view.findViewById(R.id.spinner_trainer);
        String[] items_sprTrainer= new String[]{"sinh@gmail.com", "nguyen@gmail.com", "quyet@gmail.com"};
        ArrayAdapter<String> adapter_sprTrainer = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items_sprTrainer);
        sprTrainer.setAdapter(adapter_sprTrainer);

        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialog("Add Success!");
                showFailDialog("Assignment already exist!");
            }
        });

        btnBack= view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
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