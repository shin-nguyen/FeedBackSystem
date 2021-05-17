package com.gaf.project.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaf.project.R;
import com.gaf.project.adapter.FeedbackAdapter;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Admin;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.TypeFeedback;

import java.util.ArrayList;
import java.util.List;

public class FeedBackFragment extends Fragment{

    private View view;
    private NavController navigation;
    private RecyclerView recyclerViewFeedback;
    private FeedbackAdapter feedBackAdapter;
    private List<Feedback> listFeedBack;

    private Bundle bundle = new Bundle();
    public FeedBackFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);

        recyclerViewFeedback = view.findViewById(R.id.rcv_feedback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewFeedback.setLayoutManager(linearLayoutManager);

        feedBackAdapter = new FeedbackAdapter(new FeedbackAdapter.IClickItem() {
            @Override
            public void detail(Feedback item) {
                clickDetail();
            }

            @Override
            public void update(Feedback item) {
                clickUpdate();
            }

            @Override
            public void delete(Feedback item) {
                clickDelete();
            }
        });

        Admin ad = new Admin("user", "hung", "hung@gmail.com", "123");

        listFeedBack = new ArrayList<>();

        TypeFeedback typeFeedback = new TypeFeedback(1,"Ec",false);
        Admin admin = new Admin("thao","thao","thaole","1234");

        listFeedBack.add(new Feedback(1,"Ec",admin,false,typeFeedback,new ArrayList<>()));
        listFeedBack.add(new Feedback(2,"Ec",admin,false,typeFeedback,new ArrayList<>()));
        listFeedBack.add(new Feedback(3,"Ec",admin,false,typeFeedback,new ArrayList<>()));
        
        feedBackAdapter.setData(listFeedBack);

        recyclerViewFeedback.setAdapter(feedBackAdapter);

        //open fragment create new feedback by button
        Button btnAddFeedBack = view.findViewById(R.id.btn_add_feedback);
        btnAddFeedBack.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("mission", SystemConstant.ADD);
//
//            navigation = Navigation.findNavController(view);
//            navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
            clickAdd();
        });

        return view;
    }

    private void clickAdd() {
        bundle.putString("mission", SystemConstant.ADD);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
    }

    private void clickDelete() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View viewBuilder=LayoutInflater.from(getContext()).inflate(R.layout.warning_dialog,null);

        builder.setView(viewBuilder);

        final AlertDialog warningDialog=builder.create();

        TextView warningContent = viewBuilder.findViewById(R.id.txt_warning_content);
        warningContent.setText("Do you want to delete this item?");

        warningDialog.show();

        Button btnCancel = viewBuilder.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(vi->{
            warningDialog.dismiss();
        });

        Button btnYes = viewBuilder.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(v->{
//            warningDialog.dismiss();
//
//            View viewBuilderSuccess=LayoutInflater.from(getContext()).inflate(R.layout.successful_dialog,null);
//
//            builder.setView(viewBuilderSuccess);
//
//            TextView warningSuccessContent = viewBuilderSuccess.findViewById(R.id.txt_success_dialog_message);
//            warningSuccessContent.setText("Delete Success!");
//
//            final AlertDialog successDialog=builder.create();
//            successDialog.show();
//
//            Button btnSuccessCancel = viewBuilderSuccess.findViewById(R.id.btn_success_confirm);
//            btnSuccessCancel.setOnClickListener(vi -> {
//                successDialog.dismiss();

//            });
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            SuccessDialog dialog = new SuccessDialog(SystemConstant.DELETE);
            //change dialog message using bundle
//        Bundle bundle = new Bundle();
//        bundle.putString("placeholder", "Update success");
//
//        newFragment.setArguments(bundle);
            dialog.show(ft, "dialog add success");
        });
    }

    private void clickUpdate() {
        bundle.putString("mission", SystemConstant.UPDATE);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_nav_feedback_to_add_feedback_fragment, bundle);
    }

    private void clickDetail() {
        bundle.putString("mission", SystemConstant.DETAIL);
        navigation = Navigation.findNavController(view);
        navigation.navigate(R.id.action_nav_feedback_to_review_feedback_fragment, bundle);
    }
}