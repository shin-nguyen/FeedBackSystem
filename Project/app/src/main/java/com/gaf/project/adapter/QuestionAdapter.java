package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>{

    private List<Question> mListQuestion;

    public void setData(List<Question> list){
        this.mListQuestion = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_in_feeback,parent,false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = mListQuestion.get(position);

        if (question == null)
            return;

        holder.question.setText(question.getQuestionContent());
    }

    @Override
    public int getItemCount() {
        if (mListQuestion != null)
            return mListQuestion.size();
        return 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{

        private CheckBox ckQuestion;
        private TextView question;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            ckQuestion = itemView.findViewById(R.id.checkbox_question);
            question = itemView.findViewById(R.id.txt_question_in_feeback);
        }
    }
}
