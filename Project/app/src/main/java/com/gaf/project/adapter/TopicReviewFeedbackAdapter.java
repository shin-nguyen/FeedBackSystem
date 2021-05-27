package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopicReviewFeedbackAdapter extends RecyclerView.Adapter<TopicReviewFeedbackAdapter.TopicReviewFeedbackViewHolder>{

    private View view;
    private List<Topic> mListTopic;
    private List<Question> questionList;

    public TopicReviewFeedbackAdapter(List<Question> questionList) {
        this.questionList=questionList;
    }

    public void setData(List<Topic> list){
        this.mListTopic = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public TopicReviewFeedbackViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_in_review_feedback, parent, false);
        return new TopicReviewFeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TopicReviewFeedbackViewHolder holder, int position) {
        Topic topic = mListTopic.get(position);

        if (mListTopic == null)
            return;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        holder.questionRecyclerView.setLayoutManager(layoutManager);
        holder.topic.setText(topic.getTopicName());

        QuestionReviewFeedbackAdapter questionReviewFeedbackAdapter = new QuestionReviewFeedbackAdapter();
        //get list question and setData

        questionReviewFeedbackAdapter.setData(questionList);
        //
        holder.questionRecyclerView.setAdapter(questionReviewFeedbackAdapter);
    }

    @Override
    public int getItemCount() {
        if (mListTopic != null)
            return mListTopic.size();
        return 0;
    }

    public class TopicReviewFeedbackViewHolder extends RecyclerView.ViewHolder{

        private TextView topic;
        private RecyclerView questionRecyclerView;

        public TopicReviewFeedbackViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            topic = itemView.findViewById(R.id.topic_in_review_feedback);
            questionRecyclerView = itemView.findViewById(R.id.rcv_question_in_review);
        }
    }
}
