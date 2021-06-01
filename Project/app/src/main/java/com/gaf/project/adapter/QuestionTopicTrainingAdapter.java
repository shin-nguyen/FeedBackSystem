package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Answer;
import com.gaf.project.model.Module;
import com.gaf.project.model.Question;
import com.gaf.project.model.Trainee;
import com.gaf.project.model.Class;
import com.gaf.project.utils.SessionManager;

import java.util.List;

public class QuestionTopicTrainingAdapter extends RecyclerView.Adapter<QuestionTopicTrainingAdapter.AnswerViewHolder>{

    private IClickItem iClickItem;
    private List<Question> mListQuestion;
    private Integer topicPostion;
    private Module module;
    private Class mClass;
    public QuestionTopicTrainingAdapter(IClickItem iClickItem, Integer topicPostion, Module module,Class mClass) {
        this.iClickItem = iClickItem;
        this.topicPostion = topicPostion;
        this.module = module;
        this.mClass = mClass;
    }

    public interface IClickItem{
        void check(Answer item);
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

        holder.radioStronglyDisagree.setOnClickListener(v->{
            setFalse(holder);
            holder.radioStronglyDisagree.setChecked(true);
            setAnswer(question,0);
        });
        holder.radioDisagree.setOnClickListener(v->{
            setFalse(holder);
            holder.radioDisagree.setChecked(true);
            setAnswer(question,1);
        });
        holder.radioUnDecide.setOnClickListener(v->{
            setFalse(holder);
            holder.radioUnDecide.setChecked(true);
            setAnswer(question,2);
        });
        holder.radioAgree.setOnClickListener(v->{
            setFalse(holder);
            holder.radioAgree.setChecked(true);
            setAnswer(question,3);
        });
        holder.radioStrongAgree.setOnClickListener(v->{
            setFalse(holder);
            holder.radioStrongAgree.setChecked(true);
            setAnswer(question,4);
        });

        holder.txtQuestion.setText(question.getQuestionContent());
        holder.postionTopic.setText(topicPostion.toString());
        holder.postionAnswer.setText(String.valueOf(position+1));
    }
    private void setAnswer(Question question , Integer value){
        Trainee trainee = SessionManager.getInstance().getTrainee();
        Answer answer =  new Answer(module,trainee,mClass,question,value);
        answer.setValue(value);
        iClickItem.check(answer);
    }
    private void setFalse(AnswerViewHolder holder){
        holder.radioAgree.setChecked(false);
        holder.radioDisagree.setChecked(false);
        holder.radioStronglyDisagree.setChecked(false);
        holder.radioStrongAgree.setChecked(false);
        holder.radioUnDecide.setChecked(false);

    }
    @Override
    public int getItemCount() {
        if (mListQuestion != null){
            return mListQuestion.size();
        }
        return 0;
    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder{

        private TextView txtQuestion;

        private TextView postionTopic, postionAnswer;
        private RadioButton radioStronglyDisagree,radioUnDecide,radioStrongAgree,radioDisagree,radioAgree;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);

            postionAnswer = itemView.findViewById(R.id.postion_answer);
            postionTopic = itemView.findViewById(R.id.postion_topic);

            txtQuestion = itemView.findViewById(R.id.question_do_feedback);
            radioStronglyDisagree = itemView.findViewById(R.id.radio_strongly_disagree);
            radioUnDecide = itemView.findViewById(R.id.radio_UnDecided);
            radioStrongAgree = itemView.findViewById(R.id.radio_strong_agree);
            radioDisagree = itemView.findViewById(R.id.radio_disagree);
            radioAgree = itemView.findViewById(R.id.radio_agree);

        }
    }

}

