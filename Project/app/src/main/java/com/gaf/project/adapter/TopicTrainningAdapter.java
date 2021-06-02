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
import com.gaf.project.model.Answer;
import com.gaf.project.model.Module;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.model.Trainee;
import com.gaf.project.response.QuestionResponse;
import com.gaf.project.service.QuestionService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.model.Class;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicTrainningAdapter extends RecyclerView.Adapter<TopicTrainningAdapter.TopicTrainningViewHolder>{
    private List<Topic> mListTopic;
    private View view;
    private QuestionService questionService = ApiUtils.getQuestionService();
    private List<Question> mListQuestion;
    private Module module;
    private Class mClass;

    public TopicTrainningAdapter(Module module,Class mClass) {
        this.module = module;
        this.mClass = mClass;
    }

    private List<Answer> mListAnswer = new ArrayList<>();

    public  List<Answer> getmListAnswer() {
        return mListAnswer;
    }

    public void setData(List<Topic> list){
        this.mListTopic = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopicTrainningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_trainee_module_feedback, parent, false);
        return new TopicTrainningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicTrainningViewHolder holder, int position) {
        Topic topic = mListTopic.get(position);

        if (mListTopic == null)
            return;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        Integer newPostion = position +1;
        holder.postion.setText(String.valueOf(newPostion));
        holder.answerRecyclerView.setLayoutManager(layoutManager);
        holder.name.setText(topic.getTopicName());

        QuestionTopicTrainingAdapter questionTopicTrainingAdapter =   new QuestionTopicTrainingAdapter(item -> {
            checkItem(item);
        },newPostion, module,mClass);

        mListQuestion = new ArrayList<>();
        //load list questions
        Call<QuestionResponse> questionCall = questionService.loadListQuestionByTopic(topic.getTopicID());
        questionCall.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful() && response.body()!=null){
                    mListQuestion=  response.body().getQuestions();
                    questionTopicTrainingAdapter.setData( mListQuestion);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });


        holder.answerRecyclerView.setAdapter(questionTopicTrainingAdapter);
    }


    private void checkItem(Answer item) {

        Integer postion = mListAnswer.indexOf(item);
        if (postion !=-1){
            mListAnswer.set(postion,item);
        }else{
            mListAnswer.add(item);
        }

    }

    @Override
    public int getItemCount() {
        if (mListTopic != null)
            return mListTopic.size();
        return 0;
    }


    public class TopicTrainningViewHolder extends RecyclerView.ViewHolder{

        private TextView postion,name;
        private RecyclerView answerRecyclerView;
        public TopicTrainningViewHolder(@NonNull View itemView) {
            super(itemView);

            answerRecyclerView = itemView.findViewById(R.id.rcv_answer);
            postion = itemView.findViewById(R.id.id_topic);
            name = itemView.findViewById(R.id.topic_in_trainee_feedback);
        }
    }
}
