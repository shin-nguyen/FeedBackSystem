package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gaf.project.R;
import com.gaf.project.adapter.AssignmentAdapter;
import com.gaf.project.adapter.CommentAdapter;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Class;
import com.gaf.project.model.Comment;
import com.gaf.project.model.Module;
import com.gaf.project.response.AssignmentResponse;
import com.gaf.project.response.CommentResponse;
import com.gaf.project.service.AssignmentService;
import com.gaf.project.service.CommentService;
import com.gaf.project.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCommentFragment extends Fragment {

    private CommentService commentService;
    private RecyclerView recyclerViewComment;
    private CommentAdapter commentAdapter;
    private List<Comment> listComment;

    private View view;

    public ViewCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentService = ApiUtils.getCommentService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_comment, container, false);

        if(getArguments() != null) {
            Class mClass = (Class) getArguments().getSerializable("class");
            Module module = (Module) getArguments().getSerializable("module");

            recyclerViewComment = view.findViewById(R.id.rcvComment);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerViewComment.setLayoutManager(linearLayoutManager);

            commentAdapter = new CommentAdapter();

            listComment = new ArrayList<>();

            Call<CommentResponse> call =  commentService.loadListComment(mClass.getClassID(), module.getModuleID());
            call.enqueue(new Callback<CommentResponse>() {
                @Override
                public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                    if (response.isSuccessful()&&response.body()!=null){
                        listComment = response.body().getComments();
                        commentAdapter.setData(listComment);
                        Log.e("Success","Comment get success");
                    }
                }

                @Override
                public void onFailure(Call<CommentResponse> call, Throwable t) {
                    Log.e("Error",t.getLocalizedMessage());
                    showToast("Call API fail!");
                }
            });

            recyclerViewComment.setAdapter(commentAdapter);

        }

        return view;
    }

    public void showToast(String string){
        Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
    }

}