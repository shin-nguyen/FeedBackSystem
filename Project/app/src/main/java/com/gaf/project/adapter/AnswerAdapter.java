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

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>{

    private IClickItem iClickItem;
    private List<Question> mListQuestion;

    public AnswerAdapter(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public interface IClickItem{
        void check(Question item,Boolean b);
    }

    public void setData(List<Question> list){
        this.mListQuestion = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer,parent,false);

        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        Question question = mListQuestion.get(position);

        if(question==null){
            return;
        }

        holder.chkQuestion.setOnCheckedChangeListener((compoundButton, b) -> {
            iClickItem.check(question,b);
        });
        holder.textFeedBackQuestion.setText(question.getQuestionContent());
    }

    @Override
    public int getItemCount() {
        if (mListQuestion != null){
            return mListQuestion.size();
        }
        return 0;
    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder{

        private TextView textFeedBackQuestion;
        private CheckBox chkQuestion;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);

            textFeedBackQuestion = itemView.findViewById(R.id.txt_question_in_feeback);
            chkQuestion = itemView.findViewById(R.id.checkbox_question);
        }
    }
}

