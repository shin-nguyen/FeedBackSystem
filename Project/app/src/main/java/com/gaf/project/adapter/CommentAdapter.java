package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Comment;
import com.gaf.project.utils.SessionManager;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    List<Comment> mListComment;

    public void setData(List<Comment> list){
        this.mListComment = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);

        return new CommentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        Comment comment =mListComment.get(position);

        if(comment==null){
            return;
        }

        holder.tvCommentNo.setText(String.valueOf(position));
        holder.tvCommentTraineeID.setText(comment.getTrainee().getName());
        holder.tvCommentContent.setText(comment.getComment());

    }

    @Override
    public int getItemCount() {
        if (mListComment != null){
            return mListComment.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCommentNo, tvCommentTraineeID, tvCommentContent;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCommentNo = itemView.findViewById(R.id.tvCommentNo);
            tvCommentTraineeID = itemView.findViewById(R.id.tvCommentTraineeID);
            tvCommentContent = itemView.findViewById(R.id.tvCommentContent);
        }
    }
}
