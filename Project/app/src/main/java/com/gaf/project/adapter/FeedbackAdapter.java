package com.gaf.project.adapter;


import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
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

    private IClickItem iClickItem;
    private List<Feedback> mListFeedback;

    public FeedbackAdapter(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public interface IClickItem{
        void detail(Feedback item);
        void update(Feedback item);
        void delete(Feedback item);
    }

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

        String stringOfFbId = "Feedback ID: ";
        SpannableStringBuilder feedbackId = new SpannableStringBuilder(stringOfFbId);
        feedbackId.setSpan(new StyleSpan(Typeface.BOLD), 0, stringOfFbId.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        feedbackId.append(feedback.getFeedbackID().toString());
        holder.feedbackId.setText(feedbackId);

        String stringOfTitle = "Title: ";
        SpannableStringBuilder title = new SpannableStringBuilder(stringOfTitle);
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, stringOfTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.append(feedback.getTitle());
        holder.feedbackTitle.setText(title);

        String stringOfAdId = "Admin ID: ";
        SpannableStringBuilder adId = new SpannableStringBuilder(stringOfAdId);
        adId.setSpan(new StyleSpan(Typeface.BOLD), 0, stringOfAdId.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        adId.append(feedback.getAdmin().getUserName());
        holder.adminId.setText(adId);

        holder.detailButton.setOnClickListener(v -> {
            iClickItem.detail(feedback);
        });

        holder.editButton.setOnClickListener(v -> {
            iClickItem.update(feedback);
        });

        holder.deleteButton.setOnClickListener(v -> {
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
            deleteButton = itemView.findViewById(R.id.btn_delete_feedback);
        }
    }
}
