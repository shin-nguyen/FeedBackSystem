package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gaf.project.R;

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
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter_sprMail = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items_sprMail);
        //set the spinners adapter to the previously created one.
        sprModule.setAdapter(adapter_sprMail);

        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"save",Toast.LENGTH_LONG).show();
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
}