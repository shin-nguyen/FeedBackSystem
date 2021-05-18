package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder>{

    private List<Topic> mListTopic;
    private View view;

    public void setData(List<Topic> list){
        this.mListTopic = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_in_feeback, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = mListTopic.get(position);

        if (mListTopic == null)
            return;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        holder.questionRecyclerView.setLayoutManager(layoutManager);
        //holder.questionRecyclerView.setHasFixedSize(true);
        holder.topic.setText(topic.getTopicName());

        ArrayList<Question> listQuestion = new ArrayList<>();

        if (mListTopic.get(position).getTopicName().equals("topic1")){
            listQuestion.add(new Question(1, topic, "content ddddddddddddddddddddddddddddddddddd", false, new ArrayList<>()));
            listQuestion.add(new Question(2, topic, "content2", false, new ArrayList<>()));
        }

        QuestionAdapter questionAdapter = new QuestionAdapter(new QuestionAdapter.IClickItem() {
            @Override
            public void update(Question item) {

            }

            @Override
            public void delete(Question item) {

            }
        });
        //questionAdapter.setData(listQuestion);
        holder.questionRecyclerView.setAdapter(questionAdapter);
    }

    @Override
    public int getItemCount() {
        if (mListTopic != null)
            return mListTopic.size();
        return 0;
    }


    public class TopicViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView questionRecyclerView;
        private TextView topic;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            questionRecyclerView = itemView.findViewById(R.id.rcv_question);
            topic = itemView.findViewById(R.id.topic_in_feedback);
        }
    }
}
