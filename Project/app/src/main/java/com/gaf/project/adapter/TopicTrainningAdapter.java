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
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.QuestionResponse;
import com.gaf.project.service.AnswerService;
import com.gaf.project.service.QuestionService;
import com.gaf.project.utils.ApiUtils;

import org.w3c.dom.Text;

import java.io.Serializable;
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
    private AnswerService answerService = ApiUtils.getAnswerService();
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

        holder.postion.setText(position+1);
        holder.answerRecyclerView.setLayoutManager(layoutManager);
        holder.name.setText(topic.getTopicName());

        AnswerAdapter answerAdapter = new AnswerAdapter((item, b) -> {
            checkItem(item,b);
        });

//        //load list questions
//        Call<Answer> questionCall = answerService.loadListAnswerByTopic(topic.getTopicID());
//        questionCall.enqueue(new Callback<QuestionResponse>() {
//            @Override
//            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
//                if (response.isSuccessful() && response.body()!=null){
//                    mListAnswer= response.body().getQuestions();
//                    topicQuestionAdapter.setData(listQuestion);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<QuestionResponse> call, Throwable t) {
//                Log.e("Error",t.getLocalizedMessage());
//            }
//        });


        //questionAdapter.setData(listQuestion);
//        holder.answerRecyclerView.setAdapter(topicQuestionAdapter);
    }

    private void checkItem(Question item, Boolean b) {
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
