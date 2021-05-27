package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Question;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuestionReviewFeedbackAdapter extends RecyclerView.Adapter<QuestionReviewFeedbackAdapter.QuestionReviewFeedbackViewHolder>{

    private List<Question> mListQuestion;

    public void setData(List<Question> list){
        this.mListQuestion = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public QuestionReviewFeedbackViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_in_review_feedback,parent,false);

        return new QuestionReviewFeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull QuestionReviewFeedbackViewHolder holder, int position) {
        Question question = mListQuestion.get(position);

        if (question == null){
            return;
        }

        holder.question.setText(question.getQuestionContent());
    }

    @Override
    public int getItemCount() {
        if (mListQuestion != null){
            return mListQuestion.size();
        }
        return 0;
    }

    public class QuestionReviewFeedbackViewHolder extends RecyclerView.ViewHolder{

        private TextView question;

        public QuestionReviewFeedbackViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.txt_question_in_review);
        }
    }
}
