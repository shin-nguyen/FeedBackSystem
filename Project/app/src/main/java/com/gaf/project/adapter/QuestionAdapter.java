package com.gaf.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> implements Filterable {

    private List<Question> mListQuestion;
    private List<Question> mListQuestionOld;

    private QuestionAdapter.IClickItem iClickItem;

    public interface IClickItem{
        void update(Question item);
        void delete(Question item);
    }
    public QuestionAdapter(QuestionAdapter.IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public void setData(List<Question> list){
        this.mListQuestion = list;
        this.mListQuestionOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question,parent,false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

        Question question = mListQuestion.get(position);

        if(question==null){
            return;
        }

        String paddingContent = convertToWhitespace(holder.tvContent.getText().toString().trim(),"content");
        String paddingTopicName = convertToWhitespace(holder.tvTopicName.getText().toString().trim(),"name");

        holder.topicId.setText(String.valueOf(question.getTopic().getTopicID()));
        holder.topicName.setText(paddingTopicName+ (question.getTopic().getTopicName()));
        holder.questionId.setText(String.valueOf(question.getQuestionID()));
        holder.questionContent.setText(paddingContent+ (question.getQuestionContent()));

        holder.btnEdit.setOnClickListener(v->{
            iClickItem.update(question);
        });
        holder.btnDelete.setOnClickListener(v->{
            iClickItem.delete(question);
        });

    }

    @Override
    public int getItemCount() {
        if (mListQuestion != null){
            return mListQuestion.size();
        }
        return 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{
        private TextView topicId, topicName, questionId, questionContent, tvTopicName, tvContent;
        private Button btnEdit, btnDelete;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            topicId=itemView.findViewById(R.id.question_topic_id);
            topicName=itemView.findViewById(R.id.question_topic_name);
            questionId=itemView.findViewById(R.id.question_question_id);
            questionContent=itemView.findViewById(R.id.question_question_content);
            tvTopicName=itemView.findViewById(R.id.txt_topic_name);
            tvContent=itemView.findViewById(R.id.txt_content);

            btnEdit=itemView.findViewById(R.id.btn_edit_question);
            btnDelete=itemView.findViewById(R.id.btn_delete_question);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.equals("Show All")){
                    mListQuestion = mListQuestionOld;
                }else {
                    List<Question> questionList = new ArrayList<>();
                    for(Question question : mListQuestionOld){
                        if(question.getTopic().getTopicName().toLowerCase().contains(strSearch.toLowerCase())){

                            questionList.add(question);

                        }

                        mListQuestion=questionList;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values=mListQuestion;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListQuestion=(List<Question>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public String convertToWhitespace (String string, String type){
        int length = string.length();
        if(type.equals("name")){
            length+=11;
        }
        if(type.equals("content")){
            length+=15;
        }

        String result="";
        for(int i=0;i<=length;i++){
            result+=" ";
        }
        return result;
    }
}
