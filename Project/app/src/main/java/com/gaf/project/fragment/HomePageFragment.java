package com.gaf.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Assignment;
import com.gaf.project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements View.OnClickListener {

    private View view;

    public HomePageFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String userRole = SessionManager.getInstance().getUserRole();
        if(userRole.equals(SystemConstant.TRAINEE_ROLE)){
            Navigation.findNavController(view).navigate(R.id.action_nav_homepage_to_nav_trainee_home_fragment);
        }else {
            Bundle bundle = new Bundle();
            bundle.putBoolean("home_role", true);
            Navigation.findNavController(view).navigate(R.id.action_nav_homepage_to_nav_assignment,bundle);
        }
    }

    @Override
    public void onClick(View v) {

    }
}