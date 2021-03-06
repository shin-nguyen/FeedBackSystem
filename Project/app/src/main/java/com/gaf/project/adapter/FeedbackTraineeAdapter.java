package com.gaf.project.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gaf.project.R;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Feedback;
import com.gaf.project.model.Module;
import com.gaf.project.response.AddTraineeAssignmentResponse;
import com.gaf.project.service.CommentService;
import com.gaf.project.utils.ApiUtils;
import com.gaf.project.utils.SessionManager;
import com.gaf.project.viewmodel.CommentViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackTraineeAdapter extends RecyclerView.Adapter<FeedbackTraineeAdapter.FeedbackTraineeViewHolder>{

    private IClickItem iClickItem;
    private List<Assignment> mListAssignment;
    private CommentService commentService = ApiUtils.getCommentService();

    public FeedbackTraineeAdapter(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public interface IClickItem{
        void detail(Assignment item);
    }

    public void setData(List<Assignment> list){
        this.mListAssignment = list;
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
        Assignment assignment = mListAssignment.get(position);

        if(assignment==null){
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        holder.feedbackTitle.setText(String.valueOf(assignment.getModule().getFeedback().getFeedbackID()));
        holder.classId.setText(String.valueOf(assignment.getMClass().getClassID()));
        holder.className.setText(assignment.getMClass().getClassName());
        holder.moduleId.setText(assignment.getModule().getModuleID().toString());
        holder.endTime.setText(dateFormat.format(assignment.getModule(). getFeedbackEndTime()));
        holder.moduleName.setText(assignment.getModule().getModuleName());

//        Call<AddTraineeAssignmentResponse> call = commentService.loadListCommentByTrainee(assignment.getMClass().getClassID(),assignment.getModule().getModuleID());
//        call.enqueue(new Callback<AddTraineeAssignmentResponse>() {
//            @Override
//            public void onResponse(Call<AddTraineeAssignmentResponse> call, Response<AddTraineeAssignmentResponse> response) {
//                if (response.isSuccessful() && response.body().getAdded()==0){
//
//                }
//                else{
//                    holder.status.setText("Complete");
//                    holder.btnFeedback.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddTraineeAssignmentResponse> call, Throwable t) {
//                Log.e("Error",t.getLocalizedMessage());
//            }
//        });

        holder.status.setText("InComplete");

        holder.btnFeedback.setOnClickListener(v->{
            iClickItem.detail(assignment);
        });


    }

    @Override
    public int getItemCount() {
        if (mListAssignment != null){
            return mListAssignment.size();
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

            btnFeedback = itemView.findViewById(R.id.btn_do_feedback);

        }
    }
}
