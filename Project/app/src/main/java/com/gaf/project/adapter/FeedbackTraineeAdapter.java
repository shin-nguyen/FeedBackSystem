package com.gaf.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Module;

import java.util.List;

public class FeedbackTraineeAdapter extends RecyclerView.Adapter<FeedbackTraineeAdapter.FeedbackTraineeViewHolder>{

    private IClickItem iClickItem;
    private List<Module> mListModule;

    public FeedbackTraineeAdapter(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public interface IClickItem{
        void detail(Feedback item);
    }

    public void setData(List<Module> list){
        this.mListModule = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedbackTraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_for_trainee,parent,false);

        return new FeedbackTraineeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackTraineeViewHolder holder, int position) {
        Module module = mListModule.get(position);

        if(module==null){
            return;
        }

        holder.feedbackTitle.setText(String.valueOf(module.getFeedback().getFeedbackID()));



    }

    @Override
    public int getItemCount() {
        if (mListModule != null){
            return mListModule.size();
        }
        return 0;
    }

    public class FeedbackTraineeViewHolder extends RecyclerView.ViewHolder{

        private TextView feedbackTitle, classId, className, moduleId,moduleName,endTime,status;
        private Button btnFeedback;

        public FeedbackTraineeViewHolder(@NonNull View itemView) {
            super(itemView);
            feedbackTitle =itemView.findViewById(R.id.feedback_title);

            classId =itemView.findViewById(R.id.class_id);
            className =itemView.findViewById(R.id.class_name);
            moduleId =itemView.findViewById(R.id.module_id);
            moduleName =itemView.findViewById(R.id.module_name);
            endTime =itemView.findViewById(R.id.module_feedback_end_time);
            status =itemView.findViewById(R.id.status);

            btnFeedback = itemView.findViewById(R.id.btn_feedback_detail);

        }
    }
}
