package com.gaf.project.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.QuestionResponse;
import com.gaf.project.service.QuestionService;
import com.gaf.project.utils.ApiUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder>{
    private List<Topic> mListTopic;
    private View view;
    private  List<Question> listQuestion;
    private QuestionService questionService = ApiUtils.getQuestionService();
    private List<Question> mListQuestion = new ArrayList<>();


    public  List<Question> getmListQuestion() {
        return mListQuestion;
    }
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
        holder.topic.setText(topic.getTopicName());

        TopicQuestionAdapter topicQuestionAdapter = new TopicQuestionAdapter((item, b) -> {
            checkItem(item,b);
        });

        Call<QuestionResponse> questionCall = questionService.loadListQuestionByTopic(topic.getTopicID());
        questionCall.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful() && response.body()!=null){
                    listQuestion= response.body().getQuestions();
                    topicQuestionAdapter.setData(listQuestion);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });


        //questionAdapter.setData(listQuestion);
        holder.questionRecyclerView.setAdapter(topicQuestionAdapter);
    }

    private void checkItem(Question item, Boolean b) {
        if (b){
            mListQuestion.add(item);
        }else{
            mListQuestion.remove(item);
        }
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
