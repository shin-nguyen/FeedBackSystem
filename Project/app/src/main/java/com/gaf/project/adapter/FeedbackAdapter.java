package com.gaf.project.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.fragment.AddFeedBackFragment;
import com.gaf.project.model.Class;
import com.gaf.project.model.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>{

    private FeedbackAdapter.IClickItem iClickItem;
    public interface IClickItem{
        void update(Feedback item);
        void detail(Feedback item);
        void delete(Feedback item);
    }
    public FeedbackAdapter(FeedbackAdapter.IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    private List<Feedback> mListFeedback;
    private NavController navigation;

    public void setData(List<Feedback> list){
        this.mListFeedback = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback,parent,false);

        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = mListFeedback.get(position);

        if(feedback==null){
            return;
        }

        holder.feedbackId.setText(String.valueOf(feedback.getFeedbackID()));
        holder.feedbackTitle.setText(feedback.getTitle());
        holder.adminId.setText(feedback.getAdmin().getUserName());

        holder.detailButton.setOnClickListener(v -> {
            iClickItem.detail(feedback);
        });

        holder.editButton.setOnClickListener(v -> {
            iClickItem.update(feedback);
        });
        holder.deleteButton.setOnClickListener(view -> {
            iClickItem.delete(feedback);
        });


    }

    @Override
    public int getItemCount() {
        if (mListFeedback != null){
            return mListFeedback.size();
        }
        return 0;
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder{

        private TextView feedbackId, feedbackTitle, adminId;
        private Button detailButton, editButton, deleteButton;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            feedbackId =itemView.findViewById(R.id.feedback_id);
            feedbackTitle =itemView.findViewById(R.id.feedback_title);
            adminId =itemView.findViewById(R.id.feedback_admin_id);

            detailButton = itemView.findViewById(R.id.btn_feedback_detail);
            editButton = itemView.findViewById(R.id.btn_edit_feedback);
            deleteButton =itemView.findViewById(R.id.btn_delete_feedback);
        }
    }
}
