package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>{

    private List<Feedback> mListFeedback;

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
        private Button editButton, deleteButton;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            feedbackId =itemView.findViewById(R.id.feedback_id);
            feedbackTitle =itemView.findViewById(R.id.feedback_title);
            adminId =itemView.findViewById(R.id.feedback_admin_id);
        }
    }
}
